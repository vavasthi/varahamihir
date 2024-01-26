import { AfterViewInit, Component, ElementRef, Input, ViewChild } from '@angular/core';
import { SugarReading } from '../pojo/sugar-reading';
declare var google: any;

@Component({
  selector: 'app-sugar-chart',
  standalone: true,
  imports: [],
  templateUrl: './sugar-chart.component.html',
  styleUrl: './sugar-chart.component.scss'
})
export class SugarChartComponent {
  @Input() sugarReadings?: SugarReading[];

  @ViewChild('lineChart') lineChart?: ElementRef

  drawChart = () => {

/*  const data = google.visualization.arrayToDataTable([
    ['Task', 'Hours per Day'],
    [0, 11],
    [1, 2],
    [2, 2],
    [3, 2],
    [4, 7]
  ]);*/
  if (this.sugarReadings != null) {

    const values = this.sugarReadings.map(sr => [sr.minutes, sr.reading])
    const arr = [['Minutes', 'Sugar'], ...values]
    const data = google.visualization.arrayToDataTable(arr);
    const options = {
      title: 'My Daily Activities',
      legend: {position: 'top'}
    };
  
    const chart = new google.visualization.LineChart(this.lineChart?.nativeElement);
  
    chart.draw(data, options);
      }

}

  ngAfterViewInit() {
    console.log("After view init called")
    google.charts.load('current', { 'packages': ['corechart'] });
    google.charts.setOnLoadCallback(this.drawChart);
  }
}
