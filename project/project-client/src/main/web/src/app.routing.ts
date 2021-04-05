import {RouterModule, Routes} from '@angular/router';
import {HomeComponent} from './components/home/home.component';

const appRoutes: Routes = [
    { path: ':lang/home', component: HomeComponent },
    { path: '**', redirectTo: 'en/home' }
];

export const routing = RouterModule.forRoot(appRoutes,
    {useHash: true, scrollPositionRestoration: 'enabled'});
