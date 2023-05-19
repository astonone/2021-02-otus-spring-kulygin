import {Component, OnInit} from '@angular/core';
import {UserService} from "../../services/user.service";
import {SharedService} from "../../services/shared-service";
import {Router} from "@angular/router";
import {TranslateService} from "@ngx-translate/core";
import {InterviewerDto} from "../../models/Interviewer-dto";

@Component({
    selector: 'sign-up',
    templateUrl: './sign-up.component.html',
    styleUrls: [
        './sign-up.component.css'
    ]
})

export class SignUpComponent implements OnInit {

    public newUser: InterviewerDto = new InterviewerDto(null, null, null, null, null, null, null, null);
    public isRememberMe: boolean = false;

    constructor(private userService: UserService,
                private translateService: TranslateService,
                private shared: SharedService,
                private router: Router) {
    }

    ngOnInit(): void {
    }

    public isCreateAvailable(): boolean {
        return !SharedService.isBlank(this.newUser.username) && !SharedService.isBlank(this.newUser.password) &&
            !SharedService.isBlank(this.newUser.passwordRepeat) && (this.newUser.password === this.newUser.passwordRepeat) &&
            !SharedService.isBlank(this.newUser.firstName) && !SharedService.isBlank(this.newUser.lastName);
    }

    public createUser(): void {
        let user = "";
        let created = "";
        this.translateService.get('sign-up.user').subscribe(u => user = u);
        this.translateService.get('sign-up.created').subscribe(c => created = c);
        this.userService.save(this.newUser).subscribe((data) => {
            this.shared.openSnackBar(user + data.username + created);
            this.router.navigate([this.translateService.getDefaultLang() + '/login']);
        }, error => {
            if (error.status === 403) {
                let text = "";
                this.translateService.get('snackbar.rights').subscribe(t => text = t);
                this.shared.openSnackBar(text);
            } else {
                this.shared.openSnackBar(error.error.message);
            }
        });
    }

}