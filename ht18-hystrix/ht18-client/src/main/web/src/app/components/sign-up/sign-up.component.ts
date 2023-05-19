import {Component, OnInit} from '@angular/core';
import {UserDto} from "../../models/user-dto";
import {UserService} from "../../services/user.service";
import {Router} from "@angular/router";
import {SharedService} from "../../services/shared.service";

@Component({
    selector: 'sign-up',
    templateUrl: './sign-up.component.html',
    styleUrls: [
        './sign-up.component.css'
    ]
})

export class SignUpComponent implements OnInit {

    public newUser: UserDto = new UserDto(null, null, null, null, null);
    public isRememberMe: boolean = false;

    constructor(private userService: UserService,
                private shared: SharedService,
                private router: Router) {
    }

    ngOnInit(): void {
    }

    public isCreateAvailable(): boolean {
        return !this.shared.isBlank(this.newUser.username) && !this.shared.isBlank(this.newUser.password) &&
            !this.shared.isBlank(this.newUser.passwordRepeat) && (this.newUser.password === this.newUser.passwordRepeat);
    }

    public createUser(): void {
        this.userService.save(this.newUser).subscribe((data) => {
            if (data.id === 'N/A') {
                this.shared.openSnackBar("Seems now our services unavailable. Please try again later");
            } else {
                this.shared.openSnackBar('User: ' + data.username + ' successfully created');
                this.router.navigate(['login'])
            }
        }, error => {
            this.shared.openSnackBar(error.error.message);
        });
    }

}