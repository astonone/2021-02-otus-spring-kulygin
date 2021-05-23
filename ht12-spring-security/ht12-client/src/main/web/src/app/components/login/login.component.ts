import {Component, OnInit} from '@angular/core';
import {UserService} from "../../services/user.service";
import {UserDto} from "../../models/user-dto";
import {MatSnackBar, MatSnackBarHorizontalPosition, MatSnackBarVerticalPosition} from "@angular/material/snack-bar";
import {Router} from "@angular/router";
import {SharedService} from "../../services/shared.service";

@Component({
    selector: 'login',
    templateUrl: './login.component.html',
    styleUrls: [
        './login.component.css'
    ]
})

export class LoginComponent implements OnInit {

    public isRemember: boolean = false;
    public user: UserDto = new UserDto(null, null, null);

    private horizontalPosition: MatSnackBarHorizontalPosition = 'end';
    private verticalPosition: MatSnackBarVerticalPosition = 'top';

    constructor(private userService: UserService,
                private shared: SharedService,
                private snackBar: MatSnackBar,
                private router: Router) {
    }

    ngOnInit(): void {
        if (this.userService.getLoggedUser() !== null) {
            this.router.navigate(['home']);
        }
    }

    public isLoginAvailable(): boolean {
        return !this.shared.isBlank(this.user.username) && !this.shared.isBlank(this.user.password);
    }

    public login(): void {
        if (this.isRemember) {
            localStorage.setItem('isRemember', 'true');
        } else {
            localStorage.setItem('isRemember', 'false');
        }
        this.userService.login(this.user)
            .subscribe(() => {
                this.userService.setUserToken(btoa(this.user.username + ':' + this.user.password));
                this.userService.setLoggedUser(new UserDto(null, null, null));
                this.userService.authenticate().subscribe(data => {
                    this.userService.setLoggedUser(data);
                    this.router.navigate(['home']);
                })
            }, error => {
                if (error.status === 401) {
                    this.openSnackBar("Username or password incorrect!");
                } else {
                    this.openSnackBar(error.error.message);
                }
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