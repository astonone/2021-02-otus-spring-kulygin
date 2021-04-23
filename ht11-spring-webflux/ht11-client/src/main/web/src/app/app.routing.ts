import {RouterModule, Routes} from '@angular/router';
import {HomeComponent} from './components/home/home.component';
import {CommentsComponent} from "./components/comments/comments.component";

const appRoutes: Routes = [
    {path: 'home', component: HomeComponent},
    {path: 'book/:id/comments', component: CommentsComponent},
    {path: '**', redirectTo: 'home'}
];

export const routing = RouterModule.forRoot(appRoutes, {useHash: true});
