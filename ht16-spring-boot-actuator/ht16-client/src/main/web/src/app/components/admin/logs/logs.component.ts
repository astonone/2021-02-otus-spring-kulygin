import {Component, OnInit} from '@angular/core';

import {Level, Log, LoggersResponse} from './log.model';
import {LogsService} from "../../../services/admin/logs.service";

@Component({
    selector: 'logs',
    templateUrl: './logs.component.html',
    styleUrls: [
        './logs.component.css'
    ]
})
export class LogsComponent implements OnInit {
    loggers?: Log[];
    filteredAndOrderedLoggers?: Log[];
    filter = '';
    orderProp: keyof Log = 'name';
    ascending = true;
    logs: string[] = [];

    constructor(private logsService: LogsService) {
    }

    public ngOnInit(): void {
        this.findAndExtractLoggers();
        this.logsService.getLogFile().subscribe(data => {
            this.logs = data.split('\n');
        });
    }

    public changeLevel(name: string, level: Level): void {
        this.logsService.changeLevel(name, level).subscribe(() => this.findAndExtractLoggers());
    }

    public filterAndSort(): void {
        this.filteredAndOrderedLoggers = this.loggers!.filter(
            logger => !this.filter || logger.name.toLowerCase().includes(this.filter.toLowerCase())
        ).sort((a, b) => {
            if (a[this.orderProp] < b[this.orderProp]) {
                return this.ascending ? -1 : 1;
            } else if (a[this.orderProp] > b[this.orderProp]) {
                return this.ascending ? 1 : -1;
            } else if (this.orderProp === 'level') {
                return a.name < b.name ? -1 : 1;
            }
            return 0;
        });
    }

    private findAndExtractLoggers(): void {
        this.logsService.findAll().subscribe((response: LoggersResponse) => {
            this.loggers = Object.entries(response.loggers).map(([key, logger]) => new Log(key, logger.effectiveLevel));
            this.filterAndSort();
        });
    }
}
