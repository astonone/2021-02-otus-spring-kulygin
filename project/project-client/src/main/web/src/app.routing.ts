import {RouterModule, Routes} from '@angular/router';
import {HomeComponent} from './components/home/home.component';
import {CandidatesComponent} from "./components/candidadtes/candidates.component";
import {InterviewersComponent} from "./components/interviewers/interviewers.component";
import {InterviewsComponent} from "./components/interviews/interviews.component";
import {LoginComponent} from "./components/login/login.component";
import {SignUpComponent} from "./components/sign-up/sign-up.component";
import {TemplatesComponent} from "./components/templates/templates.component";

const appRoutes: Routes = [
    {path: ':lang/home', component: HomeComponent},
    {path: ':lang/candidates', component: CandidatesComponent},
    {path: ':lang/interviewers', component: InterviewersComponent},
    {path: ':lang/interviews', component: InterviewsComponent},
    {path: ':lang/templates', component: TemplatesComponent},
    {path: ':lang/template-criteria', component: TemplatesComponent},
    {path: ':lang/login', component: LoginComponent},
    {path: ':lang/sign-up', component: SignUpComponent},
    {path: '**', redirectTo: 'en/home'}
];

export const routing = RouterModule.forRoot(appRoutes,
    {useHash: true, scrollPositionRestoration: 'enabled'});
