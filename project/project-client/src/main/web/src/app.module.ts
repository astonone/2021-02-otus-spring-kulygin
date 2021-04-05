import {NgModule} from '@angular/core';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {BrowserModule} from '@angular/platform-browser';
import {FormsModule} from '@angular/forms';
import {HttpClient, HttpClientModule} from '@angular/common/http';

import {AppComponent} from './app.component';
import {routing} from './app.routing';

import {MainUiModule} from './modules/main-ui/main-ui.module';
import {TranslateHttpLoader} from '@ngx-translate/http-loader';

/*Services*/

/*Components*/
import {HomeComponent} from './components/home/home.component';
import {TranslateLoader, TranslateModule} from "@ngx-translate/core";

export function TranslationLoaderFactory(http: HttpClient) {
    return new TranslateHttpLoader(http);
}

@NgModule({
    imports: [
        BrowserModule,
        FormsModule,
        HttpClientModule,
        routing,
        BrowserAnimationsModule,
        MainUiModule,
        TranslateModule.forRoot({
            loader: {provide: TranslateLoader, useFactory: TranslationLoaderFactory, deps: [HttpClient]}
        })
    ],
    declarations: [
        AppComponent,
        HomeComponent
    ],
    entryComponents: [],
    providers: [
        HomeComponent
    ],
    bootstrap: [AppComponent]
})

export class AppModule {
}
