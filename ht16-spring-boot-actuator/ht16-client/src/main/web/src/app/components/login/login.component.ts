import {Component, OnInit} from '@angular/core';
import {UserService} from "../../services/user.service";
import {UserDto} from "../../models/user-dto";
import {Router} from "@angular/router";
import {SharedService} from "../../services/shared.service";
import {LocalStorageService} from "../../services/local-storage.service";

@Component({
    selector: 'login',
    templateUrl: './login.component.html',
    styleUrls: [
        './login.component.css'
    ]
})

export class LoginComponent implements OnInit {

    public isRemember: boolean = false;
    public user: UserDto = new UserDto(null, null, null, null, null);

    constructor(private userService: UserService,
                private shared: SharedService,
                private localStorageService: LocalStorageService,
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
        this.userService.loginAndAuthUser(this.isRemember, this.user);
    }

}