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
import {MetricsService} from './services/admin/metrics.service';
import {HealthService} from './services/admin/health.service';
import {ConfigurationService} from './services/admin/configuration.service';
import {LogsService} from './services/admin/logs.service';

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
import {MetricsComponent} from './components/admin/metrics/metrics.component';
import {JvmMemoryComponent} from './components/admin/metrics/blocks/jvm-memory/jvm-memory.component';
import {JvmThreadsComponent} from './components/admin/metrics/blocks/jvm-threads/jvm-threads.component';
import {MetricsCacheComponent} from './components/admin/metrics/blocks/metrics-cache/metrics-cache.component';
import {MetricsDatasourceComponent} from './components/admin/metrics/blocks/metrics-datasource/metrics-datasource.component';
import {MetricsEndpointsRequestsComponent} from './components/admin/metrics/blocks/metrics-endpoints-requests/metrics-endpoints-requests.component';
import {MetricsGarbageCollectorComponent} from './components/admin/metrics/blocks/metrics-garbagecollector/metrics-garbagecollector.component';
import {MetricsModalThreadsComponent} from './components/admin/metrics/blocks/metrics-modal-threads/metrics-modal-threads.component';
import {MetricsRequestComponent} from './components/admin/metrics/blocks/metrics-request/metrics-request.component';
import {MetricsSystemComponent} from './components/admin/metrics/blocks/metrics-system/metrics-system.component';
import {HealthComponent} from './components/admin/health/health.component';
import {HealthModalComponent} from './components/admin/health/modal/health-modal.component';
import {ConfigurationComponent} from './components/admin/configuration/configuration.component';
import {LogsComponent} from './components/admin/logs/logs.component';


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
        MetricsComponent,
        JvmMemoryComponent,
        JvmThreadsComponent,
        MetricsCacheComponent,
        MetricsDatasourceComponent,
        MetricsEndpointsRequestsComponent,
        MetricsGarbageCollectorComponent,
        MetricsModalThreadsComponent,
        MetricsRequestComponent,
        MetricsSystemComponent,
        HealthComponent,
        HealthModalComponent,
        ConfigurationComponent,
        LogsComponent
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
        SharedService,
        MetricsService,
        HealthService,
        ConfigurationService,
        LogsService
    ],
    bootstrap: [AppComponent]
})

export class AppModule {
    constructor(library: FaIconLibrary) {
        library.addIconPacks(fas);
        library.addIcons(faCoffee);
    }
}
