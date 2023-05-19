import {RouterModule, Routes} from '@angular/router';
import {HomeComponent} from './components/home/home.component';
import {CommentsComponent} from "./components/comments/comments.component";
import {LoginComponent} from "./components/login/login.component";
import {SignUpComponent} from "./components/sign-up/sign-up.component";
import {PageNotFoundComponent} from "./components/page-not-found/page-not-found.component";
import {AuthGuard} from "./auth.guard";
import {MetricsComponent} from "./components/admin/metrics/metrics.component";
import {HealthComponent} from "./components/admin/health/health.component";
import {ConfigurationComponent} from "./components/admin/configuration/configuration.component";
import {LogsComponent} from "./components/admin/logs/logs.component";

const appRoutes: Routes = [
    {path: '', pathMatch: 'full', redirectTo: 'home'},
    {path: 'home', component: HomeComponent, canActivate: [AuthGuard]},
    {path: 'metrics', component: MetricsComponent, canActivate: [AuthGuard]},
    {path: 'health', component: HealthComponent, canActivate: [AuthGuard]},
    {path: 'configuration', component: ConfigurationComponent, canActivate: [AuthGuard]},
    {path: 'logs', component: LogsComponent, canActivate: [AuthGuard]},
    {path: 'book/:id/comments', component: CommentsComponent, canActivate: [AuthGuard]},
    {path: 'login', component: LoginComponent},
    {path: 'sign-up', component: SignUpComponent},
    {path: 'page-not-found', component: PageNotFoundComponent},
    {path: '**', redirectTo: 'page-not-found'}
];

export const routing = RouterModule.forRoot(appRoutes, {useHash: true});
