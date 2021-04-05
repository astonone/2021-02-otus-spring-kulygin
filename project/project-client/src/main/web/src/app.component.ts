import {Component, OnInit} from '@angular/core';
import {TranslateService} from "@ngx-translate/core";
import {Router} from "@angular/router";

@Component({
    selector: 'app-root',
    templateUrl: './app.component.html',
    styleUrls: [
        './app.component.css'
    ]
})

export class AppComponent implements OnInit {

    constructor(private translateService: TranslateService,
                private router: Router) {
    }

    ngOnInit(): void {
        if (!this.existsDefaultLanguage()) {
            this.setEnLang();
        } else {
            this.setLang(this.getCurrentLang());
        }
    }

    public setRuLang(): void {
        this.setCurrentLang('ru');
        this.translateService.setDefaultLang('ru');
        this.router.navigateByUrl(this.router.url.replace('en', 'ru'));
    }

    public setEnLang(): void {
        this.setCurrentLang('en');
        this.translateService.setDefaultLang('en');
        this.router.navigateByUrl(this.router.url.replace('ru', 'en'));
    }

    public setLang(lang: string) {
        this.translateService.setDefaultLang(lang);
    }

    public setCurrentLang(lang: string) {
        localStorage.setItem('lang', lang);
    }

    public getCurrentLang() {
        return localStorage.getItem('lang');
    }

    public existsDefaultLanguage() {
        return localStorage.getItem('lang') !== null;
    }
}
