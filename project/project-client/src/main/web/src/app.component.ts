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

    public setLang(lang: string): void {
        this.translateService.setDefaultLang(lang);
    }

    public setCurrentLang(lang: string): void {
        localStorage.setItem('lang', lang);
    }

    public getCurrentLang(): string {
        return localStorage.getItem('lang');
    }

    public existsDefaultLanguage(): boolean {
        return localStorage.getItem('lang') !== null;
    }

    public goto(route: string): void {
        this.router.navigate([this.translateService.getDefaultLang() + '/' + route]);
    }

    public isManPage(): boolean {
        return this.router.url.includes('/home');
    }

    public isCandidatesPage(): boolean {
        return this.router.url.includes('/candidates');
    }

    public isInterviewersPage(): boolean {
        return this.router.url.includes('/interviewers');
    }

    public isInterviewPage(): boolean {
        return this.router.url.includes('/interviews');
    }

    public isInterviewTemplatePage(): boolean {
        return this.router.url.includes('/templates');
    }

    public isInterviewTemplateCriteriaPage(): boolean {
        return this.router.url.includes('/template-criteria');
    }
}
