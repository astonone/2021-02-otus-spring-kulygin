import {RouterModule, Routes} from '@angular/router';
import {HomeComponent} from './components/home/home.component';
import {CandidatesComponent} from "./components/candidadtes/candidates.component";
import {InterviewersComponent} from "./components/interviewers/interviewers.component";
import {InterviewsComponent} from "./components/interviews/interviews.component";
import {LoginComponent} from "./components/login/login.component";
import {SignUpComponent} from "./components/sign-up/sign-up.component";
import {TemplatesComponent} from "./components/templates/templates.component";
import {TemplateCriteriaComponent} from "./components/template-criteria/template-criteria.component";
import {EditCriteriasComponent} from "./components/edit-criterias/edit-criterias.component";
import {InterviewComponent} from "./components/interview/interview.component";
import {AuthGuard} from "./auth.guard";
import {BatchComponent} from './components/batch/batch.component';

const appRoutes: Routes = [
    {path: '', pathMatch: 'full', redirectTo: 'en/home'},
    {path: ':lang/home', component: HomeComponent, canActivate: [AuthGuard]},
    {path: ':lang/candidates', component: CandidatesComponent, canActivate: [AuthGuard]},
    {path: ':lang/interviewers', component: InterviewersComponent, canActivate: [AuthGuard]},
    {path: ':lang/interviews', component: InterviewsComponent, canActivate: [AuthGuard]},
    {path: ':lang/templates', component: TemplatesComponent, canActivate: [AuthGuard]},
    {path: ':lang/template-criteria', component: TemplateCriteriaComponent, canActivate: [AuthGuard]},
    {path: ':lang/login', component: LoginComponent},
    {path: ':lang/sign-up', component: SignUpComponent},
    {path: ':lang/edit-criterias/:id', component: EditCriteriasComponent, canActivate: [AuthGuard]},
    {path: ':lang/interview/:id', component: InterviewComponent, canActivate: [AuthGuard]},
    {path: ':lang/batch', component: BatchComponent, canActivate: [AuthGuard]},
    {path: '**', redirectTo: 'page-not-found'}
];

export const routing = RouterModule.forRoot(appRoutes,
    {useHash: true, scrollPositionRestoration: 'enabled'});
