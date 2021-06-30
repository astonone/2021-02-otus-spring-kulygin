import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {LocalStorageService} from "../local-storage.service";
import {UserService} from "../user.service";
import {Level, LoggersResponse} from "../../components/admin/logs/log.model";

@Injectable({providedIn: 'root'})
export class LogsService {
    private readonly SERVER_URL: string;

    private readonly LOGGERS: string;
    private UPDATE_LOGGER: string;

    constructor(private localStorageService: LocalStorageService,
                private http: HttpClient,
                private userService: UserService) {
        this.SERVER_URL = this.localStorageService.getServerURL();
        this.LOGGERS = this.SERVER_URL + '/management/loggers/';
        this.UPDATE_LOGGER = this.SERVER_URL + '/management/loggers/{name}';
    }

    public changeLevel(name: string, configuredLevel: Level): Observable<{}> {
        const regExp = /{name}/gi;
        const url = this.UPDATE_LOGGER.replace(regExp, name);
        return this.http.post(url, {configuredLevel}, this.userService.getOptions());
    }

    public findAll(): Observable<LoggersResponse> {
        return this.http.get<LoggersResponse>(this.LOGGERS, this.userService.getOptions());
    }
}
