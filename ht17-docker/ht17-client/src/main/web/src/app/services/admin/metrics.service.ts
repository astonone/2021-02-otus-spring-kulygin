import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Metrics, ThreadDump} from "../../components/admin/metrics/metrics.model";
import {LocalStorageService} from "../local-storage.service";
import {UserService} from "../user.service";

@Injectable({providedIn: 'root'})
export class MetricsService {

    private readonly SERVER_URL: string;

    private readonly METRICS: string;
    private readonly THREAD_DUMP: string;

    constructor(private localStorageService: LocalStorageService,
                private http: HttpClient,
                private userService: UserService) {
        this.SERVER_URL = this.localStorageService.getServerURL();
        this.METRICS = this.SERVER_URL + '/management/jhimetrics/';
        this.THREAD_DUMP = this.SERVER_URL + '/management/threaddump/';
    }

    public getMetrics(): Observable<Metrics> {
        return this.http.get<Metrics>(this.METRICS, this.userService.getOptions());
    }

    public threadDump(): Observable<ThreadDump> {
        return this.http.get<ThreadDump>(this.THREAD_DUMP, this.userService.getOptions());
    }
}
