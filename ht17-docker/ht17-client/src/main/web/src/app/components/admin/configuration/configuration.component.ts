import {Component, OnInit} from '@angular/core';

import {Bean, PropertySource} from './configuration.model';
import {ConfigurationService} from "../../../services/admin/configuration.service";

@Component({
    selector: 'configuration',
    templateUrl: './configuration.component.html',
    styleUrls: [
        './configuration.component.css'
    ]
})
export class ConfigurationComponent implements OnInit {
    allBeans!: Bean[];
    beans: Bean[] = [];
    beansFilter = '';
    beansAscending = true;
    propertySources: PropertySource[] = [];

    public constructor(private configurationService: ConfigurationService) {
    }

    public ngOnInit(): void {
        this.configurationService.getBeans().subscribe(beans => {
            this.allBeans = beans;
            this.filterAndSortBeans();
        });

        this.configurationService.getPropertySources().subscribe(propertySources => (this.propertySources = propertySources));
    }

    public filterAndSortBeans(): void {
        const beansAscendingValue = this.beansAscending ? -1 : 1;
        const beansAscendingValueReverse = this.beansAscending ? 1 : -1;
        this.beans = this.allBeans
            .filter(bean => !this.beansFilter || bean.prefix.toLowerCase().includes(this.beansFilter.toLowerCase()))
            .sort((a, b) => (a.prefix < b.prefix ? beansAscendingValue : beansAscendingValueReverse));
    }
}
