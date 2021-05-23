import {RouterModule, Routes} from '@angular/router';
import {HomeComponent} from './components/home/home.component';
import {CommentsComponent} from "./components/comments/comments.component";
import {LoginComponent} from "./components/login/login.component";
import {SignUpComponent} from "./components/sign-up/sign-up.component";

const appRoutes: Routes = [
    {path: 'home', component: HomeComponent},
    {path: 'book/:id/comments', component: CommentsComponent},
    {path: 'login', component: LoginComponent},
    {path: 'sign-up', component: SignUpComponent},
    {path: '**', redirectTo: 'login'}
];

export const routing = RouterModule.forRoot(appRoutes, {useHash: true});
