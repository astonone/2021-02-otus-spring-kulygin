import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {LocalStorageService} from "../local-storage.service";
import {UserService} from "../user.service";
import {Health} from "../../components/admin/health/health.model";

@Injectable({providedIn: 'root'})
export class HealthService {
    private readonly SERVER_URL: string;

    private readonly HEALTH: string;

    constructor(private localStorageService: LocalStorageService,
                private http: HttpClient,
                private userService: UserService) {
        this.SERVER_URL = this.localStorageService.getServerURL();
        this.HEALTH = this.SERVER_URL + '/management/health/';
    }

    public checkHealth(): Observable<Health> {
        return this.http.get<Health>(this.HEALTH, this.userService.getOptions());
    }
}
