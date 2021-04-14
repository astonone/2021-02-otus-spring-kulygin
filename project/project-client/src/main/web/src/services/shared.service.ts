import {Injectable} from '@angular/core';
import {environment} from "../environments/environment";


@Injectable({
    providedIn: 'root'
})
export class SharedService {

    private isProd: boolean = environment.production;

    private HOST_DEV = 'http://localhost';
    private PORT_DEV = '8080';

    private HOST_PROD = 'http://localhost:8080';

    private readonly SERVER_URL: string;

    constructor() {
        this.SERVER_URL = this.getServerURL();
    }

    public getServerURL() {
        return this.isProd ? (this.HOST_PROD) : (this.HOST_DEV + ':' + this.PORT_DEV);
    }

    public setTheme(theme: string) {
        localStorage.setItem('theme', theme);
    }

    public getThemeAndApply() {
        const theme = localStorage.getItem('theme');
        const themeElement: any = document.getElementById('themeAsset');
        themeElement.href = '/assets/themes/' + theme + '.css';
    }

    public isMobile = () => screen.width < 768;

    public isUserLogged = () => true;

}