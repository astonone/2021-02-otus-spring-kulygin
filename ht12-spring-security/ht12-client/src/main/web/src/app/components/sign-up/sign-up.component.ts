import {Component, OnInit} from '@angular/core';
import {MatSnackBar, MatSnackBarHorizontalPosition, MatSnackBarVerticalPosition} from "@angular/material/snack-bar";
import {UserDto} from "../../models/user-dto";
import {UserService} from "../../services/user.service";
import {SharedService} from "../../services/shared.service";
import {Router} from "@angular/router";

@Component({
    selector: 'sign-up',
    templateUrl: './sign-up.component.html',
    styleUrls: [
        './sign-up.component.css'
    ]
})

export class SignUpComponent implements OnInit {

    public newUser: UserDto = new UserDto(null, null, null);
    public isRememberMe: boolean = false;

    private horizontalPosition: MatSnackBarHorizontalPosition = 'end';
    private verticalPosition: MatSnackBarVerticalPosition = 'top';

    constructor(private userService: UserService,
                private shared: SharedService,
                private snackBar: MatSnackBar,
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
            this.openSnackBar('User: ' + data.username + ' successfully created');
            this.router.navigate(['login'])
        }, error => {
            this.openSnackBar(error.error.message);
        });
    }

    private openSnackBar(snackBarText: string): void {
        this.snackBar.open(snackBarText, 'End now', {
            duration: 2000,
            horizontalPosition: this.horizontalPosition,
            verticalPosition: this.verticalPosition,
        });
    }

}