import {ChangeDetectionStrategy, Component, Input} from '@angular/core';
import {CacheMetrics} from "../../metrics.model";
import {filterNaN} from '../../../utils/operators';

@Component({
    selector: 'jhi-metrics-cache',
    templateUrl: './metrics-cache.component.html',
    changeDetection: ChangeDetectionStrategy.OnPush,
})
export class MetricsCacheComponent {
    /**
     * object containing all cache related metrics
     */
    @Input() cacheMetrics?: { [key: string]: CacheMetrics };

    /**
     * boolean field saying if the metrics are in the process of being updated
     */
    @Input() updating?: boolean;

    public filterNaN = (input: number): number => filterNaN(input);
}
