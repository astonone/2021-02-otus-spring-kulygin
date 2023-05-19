import {Component, ElementRef, Inject, OnInit} from '@angular/core';
import {TranslateService} from "@ngx-translate/core";
import {Router} from "@angular/router";
import {DOCUMENT} from "@angular/common";
import {SharedService} from "./services/shared-service";
import {UserService} from "./services/user.service";
import {InterviewerDto} from "./models/Interviewer-dto";

@Component({
    selector: 'app-root',
    templateUrl: './app.component.html',
    styleUrls: [
        './app.component.css'
    ]
})

export class AppComponent implements OnInit {

    constructor(private translateService: TranslateService,
                public userService: UserService,
                public shared: SharedService,
                private router: Router,
                @Inject(DOCUMENT) document: ElementRef) {

        if (localStorage.getItem('theme') === null) {
            this.onSetTheme('indigo-pink');
        } else {
            this.shared.getThemeAndApply();
        }
    }

    ngOnInit(): void {
        if (!this.existsDefaultLanguage()) {
            this.setEnLang();
        } else {
            this.setLang(this.getCurrentLang());
        }
    }

    public onSetTheme(theme: string) {
        const themeElement: any = document.getElementById('themeAsset');
        themeElement.href = '/assets/themes/' + theme + '.css';
        this.shared.setTheme(theme);
    }

    public isSelected(theme: string) {
        const themeElement: any = document.getElementById('themeAsset');
        return themeElement.href.toString().includes(theme);
    }

    public toggleSidenav(sidenav: any) {
        if (this.shared.isMobile()) {
            sidenav.toggle();
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

    public logout(): void {
        this.userService.setUserToken('');
        this.userService.setLoggedUser(new InterviewerDto(null, null, null, null, null, null, null, null));
        this.router.navigate([this.translateService.getDefaultLang() + '/login']);
    }
}
