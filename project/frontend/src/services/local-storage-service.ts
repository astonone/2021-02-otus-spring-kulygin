import {Injectable} from '@angular/core';
import {environment} from "../environments/environment";


@Injectable({
    providedIn: 'root'
})
export class LocalStorageService {

    private isProd: boolean = environment.production;

    private HOST_DEV = '/common-api';

    private HOST_PROD = '/common-api';

    private readonly SERVER_URL: string;

    constructor() {
        this.SERVER_URL = this.isProd ? this.HOST_PROD : this.HOST_DEV;
    }

    public getStorage = () => localStorage.getItem('isRemember') === 'true' ? localStorage : sessionStorage;

    public getServerURL() {
        return this.SERVER_URL;
    }

}