import {Injectable} from "@angular/core";
import {LocalStorageService} from "./local-storage-service";
import {SharedService} from "./shared-service";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Router} from "@angular/router";
import {Observable} from "rxjs";
import {InterviewerDto} from "../models/Interviewer-dto";
import {TranslateService} from "@ngx-translate/core";

@Injectable({
    providedIn: 'root'
})
export class UserService {

    private readonly SERVER_URL: string;

    private readonly USER_LOGIN: string;
    private readonly USER_AUTHENTICATE: string;
    private readonly GET_BY_ID: string;
    private readonly DELETE: string;
    private readonly SAVE: string;

    constructor(private localStorageService: LocalStorageService,
                private shared: SharedService,
                private http: HttpClient,
                private router: Router,
                private translateService: TranslateService) {
        this.SERVER_URL = this.localStorageService.getServerURL();
        this.USER_LOGIN = this.SERVER_URL + '/user-service/interviewer/login';
        this.USER_AUTHENTICATE = this.SERVER_URL + '/user-service/interviewer/authenticate';
        this.GET_BY_ID = this.SERVER_URL + '/user-service/interviewer/{id}';
        this.DELETE = this.SERVER_URL + '/user-service/interviewer/{id}';
        this.SAVE = this.SERVER_URL + '/user-service/interviewer/';
    }

    public setUserToken(token: string): void {
        this.localStorageService.getStorage().setItem('token', token);
    }

    public getUserToken(): string {
        return this.localStorageService.getStorage().getItem('token');
    }

    public setLoggedUser(user: InterviewerDto): void {
        if (user.id !== null) {
            this.localStorageService.getStorage().setItem('loggedUser', JSON.stringify(InterviewerDto.createNewObjectFromDto(user).toObject()));
        } else {
            this.localStorageService.getStorage().setItem('loggedUser', '');
        }
    }

    public getLoggedUser(): InterviewerDto {
        let user = this.localStorageService.getStorage().getItem('loggedUser');
        if (user !== null && user !== '') {
            let parsedUser = JSON.parse(user);
            return new InterviewerDto(parsedUser.id, parsedUser.firstName, parsedUser.lastName,
                parsedUser.positionType, parsedUser.username, parsedUser.password, parsedUser.role, null);
        } else {
            return null;
        }
    }

    public isUserLogged(): boolean {
        return this.getLoggedUser() !== null;
    }
    public isDevUser(): boolean {
        return this.getLoggedUser().role === 'ROLE_DEVELOPER';
    }

    public getOptions() {
        const headers: HttpHeaders = new HttpHeaders({
            'Authorization': 'Basic ' + this.getUserToken()
        });

        return {headers: headers};
    }

    public login(interviewerDto: InterviewerDto): Observable<InterviewerDto> {
        return this.http.post<InterviewerDto>(this.USER_LOGIN, InterviewerDto.createNewObjectFromDto(interviewerDto).toObject());
    }

    public getById(id: string): Observable<InterviewerDto> {
        const regExp = /{id}/gi;
        const url = this.GET_BY_ID.replace(regExp, id);
        return this.http.get<InterviewerDto>(url, this.getOptions());
    }

    public save(user: InterviewerDto): Observable<InterviewerDto> {
        let interviewerDto = InterviewerDto.createNewObjectFromDto(user);
        interviewerDto.create = true;
        return this.http.post<InterviewerDto>(this.SAVE, interviewerDto.toObject());
    }

    public removeById(id: string): Observable<Object> {
        const regExp = /{id}/gi;
        const url = this.DELETE.replace(regExp, id);
        return this.http.delete<Observable<Object>>(url, this.getOptions());
    }

    public loginAndAuthUser(isRemember: boolean, user: InterviewerDto) {
        if (isRemember) {
            localStorage.setItem('isRemember', 'true');
        } else {
            localStorage.setItem('isRemember', 'false');
        }
        this.login(user)
            .subscribe((data) => {
                this.setUserToken(btoa(user.username + ':' + user.password));
                this.setLoggedUser(new InterviewerDto(null, null, null, null, null, null, null, null));
                this.getById(data.id).subscribe(data => {
                    this.setLoggedUser(data);
                    this.router.navigate([this.translateService.getDefaultLang() + '/home']);
                })
            }, error => {
                if (error.status === 401) {
                    this.shared.openSnackBar("Username or password incorrect!");
                } else {
                    this.shared.openSnackBar(error.error.message);
                }
            });
    }

}