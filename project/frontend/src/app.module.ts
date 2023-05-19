import {NgModule} from '@angular/core';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {BrowserModule} from '@angular/platform-browser';
import {FormsModule} from '@angular/forms';
import {HttpClient, HttpClientModule} from '@angular/common/http';

import {AppComponent} from './app.component';
import {routing} from './app.routing';

import {MainUiModule} from './modules/main-ui/main-ui.module';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import {FaIconLibrary, FontAwesomeModule} from '@fortawesome/angular-fontawesome';
import {faCoffee, fas} from '@fortawesome/free-solid-svg-icons';

import {TranslateHttpLoader} from '@ngx-translate/http-loader';
import {TranslateLoader, TranslateModule} from "@ngx-translate/core";

/*Services*/
import {LocalStorageService} from "./services/local-storage-service";
import {InterviewersService} from "./services/interviewers-service";
import {InterviewTemplateCriteriaService} from "./services/interview-template-criteria-service";
import {CandidateService} from "./services/candidate-service";
import {TemplateService} from "./services/template-service";
import {InterviewService} from "./services/interview-service";
import {SharedService} from "./services/shared-service";
import {UserService} from "./services/user.service";

/*Components*/
import {HomeComponent} from './components/home/home.component';
import {CandidatesComponent} from './components/candidadtes/candidates.component';
import {InterviewersComponent} from "./components/interviewers/interviewers.component";
import {InterviewsComponent} from "./components/interviews/interviews.component";
import {LoginComponent} from "./components/login/login.component";
import {SignUpComponent} from "./components/sign-up/sign-up.component";
import {TemplatesComponent} from "./components/templates/templates.component";
import {TemplateCriteriaComponent} from "./components/template-criteria/template-criteria.component";
import {EditCriteriasComponent} from "./components/edit-criterias/edit-criterias.component";
import {InterviewComponent} from "./components/interview/interview.component";
import {PageNotFoundComponent} from "./components/page-not-found/page-not-found.component";
import {BatchComponent} from "./components/batch/batch.component";

/*Components UI*/
import {RateStarsComponent} from "./components/ui/rate-stars/rate-stars.component";

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
        FontAwesomeModule,
        NgbModule,
        TranslateModule.forRoot({
            loader: {provide: TranslateLoader, useFactory: TranslationLoaderFactory, deps: [HttpClient]}
        })
    ],
    declarations: [
        AppComponent,
        HomeComponent,
        CandidatesComponent,
        InterviewersComponent,
        InterviewsComponent,
        LoginComponent,
        SignUpComponent,
        TemplatesComponent,
        TemplateCriteriaComponent,
        EditCriteriasComponent,
        InterviewComponent,
        RateStarsComponent,
        PageNotFoundComponent,
        BatchComponent
    ],
    entryComponents: [],
    providers: [
        LocalStorageService,
        InterviewersService,
        InterviewTemplateCriteriaService,
        CandidateService,
        TemplateService,
        InterviewService,
        UserService,
        SharedService
    ],
    bootstrap: [AppComponent]
})

export class AppModule {
    constructor(library: FaIconLibrary) {
        library.addIconPacks(fas);
        library.addIcons(faCoffee);
    }
}
