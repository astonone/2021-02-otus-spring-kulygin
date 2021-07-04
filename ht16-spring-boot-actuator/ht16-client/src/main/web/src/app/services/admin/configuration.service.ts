import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {map} from 'rxjs/operators';
import {Bean, Beans, ConfigProps, Env, PropertySource} from "../../components/admin/configuration/configuration.model";
import {LocalStorageService} from "../local-storage.service";
import {UserService} from "../user.service";


@Injectable({providedIn: 'root'})
export class ConfigurationService {

    private readonly SERVER_URL: string;

    private readonly BEANS: string;
    private readonly PROPERTY_SOURCES: string;

    constructor(private localStorageService: LocalStorageService,
                private http: HttpClient,
                private userService: UserService) {
        this.SERVER_URL = this.localStorageService.getServerURL();
        this.BEANS = this.SERVER_URL + '/management/configprops/';
        this.PROPERTY_SOURCES = this.SERVER_URL + '/management/env/';
    }

    public getBeans(): Observable<Bean[]> {
        return this.http.get<ConfigProps>(this.BEANS, this.userService.getOptions()).pipe(
            map(configProps =>
                Object.values(
                    Object.values(configProps.contexts)
                        .map(context => context.beans)
                        .reduce((allBeans: Beans, contextBeans: Beans) => ({...allBeans, ...contextBeans}))
                )
            )
        );
    }

    public getPropertySources(): Observable<PropertySource[]> {
        return this.http.get<Env>(this.PROPERTY_SOURCES, this.userService.getOptions()).pipe(map(env => env.propertySources));
    }
}
