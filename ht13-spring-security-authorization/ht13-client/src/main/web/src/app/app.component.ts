import {Component} from '@angular/core';
import {UserService} from "./services/user.service";
import {Router} from "@angular/router";
import {UserDto} from "./models/user-dto";

@Component({
    selector: 'app-root',
    templateUrl: './app.component.html',
    styleUrls: [
        './app.component.css'
    ]
})

export class AppComponent {

    constructor(public userService: UserService,
                private router: Router) {
    }

    public logout(): void {
        this.userService.setUserToken('');
        this.userService.setLoggedUser(new UserDto(null, null, null, null));
        this.router.navigate(['/login']);
    }

}
