import {NgModule} from '@angular/core';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {BrowserModule} from '@angular/platform-browser';
import {FormsModule} from '@angular/forms';
import {HttpClientModule} from '@angular/common/http';

import {AppComponent} from './app.component';
import {routing} from './app.routing';

import {MainUiModule} from './modules/main-ui/main-ui.module';

/*Services*/
import {BookService} from "./services/book.service";
import {AuthorService} from "./services/author.service";
import {GenreService} from "./services/genre.service";
import {SharedService} from "./services/shared.service";

/*Components*/
import {HomeComponent} from './components/home/home.component';
import {CommentsComponent} from './components/comments/comments.component';
import {GenreComponent} from "./components/genre/genre.component";
import {AuthorComponent} from "./components/author/author.component";
import {BookComponent} from "./components/book/book.component";

@NgModule({
    imports: [
        BrowserModule,
        FormsModule,
        HttpClientModule,
        routing,
        BrowserAnimationsModule,
        MainUiModule
    ],
    declarations: [
        AppComponent,
        HomeComponent,
        GenreComponent,
        AuthorComponent,
        BookComponent,
        CommentsComponent
    ],
    entryComponents: [],
    providers: [
        HomeComponent,
        GenreComponent,
        AuthorComponent,
        BookComponent,
        CommentsComponent,
        AuthorService,
        GenreService,
        BookService,
        SharedService
    ],
    bootstrap: [AppComponent]
})

export class AppModule {
}
