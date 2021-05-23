import {Injectable} from '@angular/core';
import {SharedService} from "./shared.service";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";
import {UserDto} from "../models/user-dto";

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

    constructor(private shared: SharedService,
                private http: HttpClient) {
        this.SERVER_URL = this.shared.getServerURL();
        this.USER_LOGIN = this.SERVER_URL + '/user/login';
        this.USER_AUTHENTICATE = this.SERVER_URL + '/user/authenticate';
        this.GET_BY_ID = this.SERVER_URL + '/user/{id}';
        this.DELETE = this.SERVER_URL + '/user/{id}';
        this.SAVE = this.SERVER_URL + '/user/';
    }

    public setUserToken(token: string): void {
        this.shared.getStorage().setItem('token', token);
    }

    public getUserToken(): string {
        return this.shared.getStorage().getItem('token');
    }

    public setLoggedUser(user: UserDto): void {
        if (user.id !== null) {
            this.shared.getStorage().setItem('loggedUser', JSON.stringify(UserDto.createNewObjectFromDto(user).toObject()));
        } else {
            this.shared.getStorage().setItem('loggedUser', '');
        }
    }

    public getLoggedUser(): UserDto {
        let user = this.shared.getStorage().getItem('loggedUser');
        if (user !== null && user !== '') {
            let parsedUser = JSON.parse(user);
            return new UserDto(parsedUser.id, parsedUser.username, parsedUser.password);
        } else {
            return null;
        }
    }

    public isUserLogged(): boolean {
        return this.getLoggedUser() !== null;
    }

    public getOptions() {
        const headers: HttpHeaders = new HttpHeaders({
            'Authorization': 'Basic ' + this.getUserToken()
        });

        return {headers: headers};
    }

    public login(userDto: UserDto): Observable<Object> {
        return this.http.post<Observable<Object>>(this.USER_LOGIN, UserDto.createNewObjectFromDto(userDto).toObject());
    }

    public authenticate(): Observable<UserDto> {
        return this.http.post<UserDto>(this.USER_AUTHENTICATE, null, this.getOptions());
    }

    public getById(id: string): Observable<UserDto> {
        const regExp = /{id}/gi;
        const url = this.GET_BY_ID.replace(regExp, id);
        return this.http.get<UserDto>(url, this.getOptions());
    }

    public save(user: UserDto): Observable<UserDto> {
        return this.http.post<UserDto>(this.SAVE, UserDto.createNewObjectFromDto(user).toObject());
    }

    public removeById(id: string): Observable<Object> {
        const regExp = /{id}/gi;
        const url = this.DELETE.replace(regExp, id);
        return this.http.delete<Observable<Object>>(url, this.getOptions());
    }

}