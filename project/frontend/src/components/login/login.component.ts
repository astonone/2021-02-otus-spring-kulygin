import {Component, OnInit} from '@angular/core';
import {UserService} from "../../services/user.service";
import {Router} from "@angular/router";
import {SharedService} from "../../services/shared-service";
import {InterviewerDto} from "../../models/Interviewer-dto";
import {TranslateService} from "@ngx-translate/core";

@Component({
    selector: 'login',
    templateUrl: './login.component.html',
    styleUrls: [
        './login.component.css'
    ]
})

export class LoginComponent implements OnInit {

    public isRemember: boolean = false;
    public user: InterviewerDto = new InterviewerDto(null, null, null, null, null, null, null, null);

    constructor(private userService: UserService,
                private translateService: TranslateService,
                private router: Router) {
    }

    ngOnInit(): void {
        if (this.userService.getLoggedUser() !== null) {
            this.router.navigate([this.translateService.getDefaultLang() + '/home']);
        }
    }

    public isLoginAvailable(): boolean {
        return !SharedService.isBlank(this.user.username) && !SharedService.isBlank(this.user.password);
    }

    public login(): void {
        this.userService.loginAndAuthUser(this.isRemember, this.user);
    }

}