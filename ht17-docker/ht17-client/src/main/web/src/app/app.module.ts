import {NgModule} from '@angular/core';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {BrowserModule} from '@angular/platform-browser';
import {FormsModule} from '@angular/forms';
import {HttpClientModule} from '@angular/common/http';

import {AppComponent} from './app.component';
import {routing} from './app.routing';

import {MainUiModule} from './modules/main-ui/main-ui.module';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';


import {FaIconLibrary, FontAwesomeModule} from '@fortawesome/angular-fontawesome';
import {faCoffee, fas} from '@fortawesome/free-solid-svg-icons';

/*Services*/
import {BookService} from "./services/book.service";
import {AuthorService} from "./services/author.service";
import {GenreService} from "./services/genre.service";
import {LocalStorageService} from "./services/local-storage.service";
import {UserService} from "./services/user.service";
import {SharedService} from "./services/shared.service";
import {MetricsService} from "./services/admin/metrics.service";
import {HealthService} from "./services/admin/health.service";
import {LogsService} from "./services/admin/logs.service";

/*Components*/
import {HomeComponent} from './components/home/home.component';
import {CommentsComponent} from './components/comments/comments.component';
import {GenreComponent} from "./components/genre/genre.component";
import {AuthorComponent} from "./components/author/author.component";
import {BookComponent} from "./components/book/book.component";
import {LoginComponent} from "./components/login/login.component";
import {SignUpComponent} from "./components/sign-up/sign-up.component";
import {PageNotFoundComponent} from "./components/page-not-found/page-not-found.component";
import {MetricsComponent} from "./components/admin/metrics/metrics.component";
import {JvmMemoryComponent} from "./components/admin/metrics/blocks/jvm-memory/jvm-memory.component";
import {JvmThreadsComponent} from "./components/admin/metrics/blocks/jvm-threads/jvm-threads.component";
import {MetricsCacheComponent} from "./components/admin/metrics/blocks/metrics-cache/metrics-cache.component";
import {MetricsDatasourceComponent} from "./components/admin/metrics/blocks/metrics-datasource/metrics-datasource.component";
import {MetricsEndpointsRequestsComponent} from "./components/admin/metrics/blocks/metrics-endpoints-requests/metrics-endpoints-requests.component";
import {MetricsGarbageCollectorComponent} from "./components/admin/metrics/blocks/metrics-garbagecollector/metrics-garbagecollector.component";
import {MetricsModalThreadsComponent} from "./components/admin/metrics/blocks/metrics-modal-threads/metrics-modal-threads.component";
import {MetricsRequestComponent} from "./components/admin/metrics/blocks/metrics-request/metrics-request.component";
import {MetricsSystemComponent} from "./components/admin/metrics/blocks/metrics-system/metrics-system.component";
import {HealthComponent} from "./components/admin/health/health.component";
import {HealthModalComponent} from "./components/admin/health/modal/health-modal.component";
import {ConfigurationService} from "./services/admin/configuration.service";
import {ConfigurationComponent} from "./components/admin/configuration/configuration.component";
import {LogsComponent} from "./components/admin/logs/logs.component";

@NgModule({
    imports: [
        BrowserModule,
        FormsModule,
        HttpClientModule,
        routing,
        BrowserAnimationsModule,
        MainUiModule,
        FontAwesomeModule,
        NgbModule
    ],
    declarations: [
        AppComponent,
        HomeComponent,
        GenreComponent,
        AuthorComponent,
        BookComponent,
        CommentsComponent,
        LoginComponent,
        SignUpComponent,
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
        AuthorService,
        GenreService,
        BookService,
        LocalStorageService,
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
