import { AfterViewInit, Component, ElementRef, Input, ViewChild } from '@angular/core';
import {MatCardModule} from '@angular/material/card';
import { SugarReading } from '../pojo/sugar-reading';
declare var google: any;

@Component({
  selector: 'app-sugar-chart',
  standalone: true,
  imports: [MatCardModule],
  templateUrl: './sugar-chart.component.html',
  styleUrl: './sugar-chart.component.scss'
})
export class SugarChartComponent {
  @Input() sugarReadings?: SugarReading[];

  @ViewChild('lineChart') lineChart?: ElementRef

  drawChart = () => {

  if (this.sugarReadings != null) {

    const values = this.sugarReadings.map(sr => [sr.minutes, sr.reading])
    const arr = [['Minutes', 'Sugar'], ...values]
    const data = google.visualization.arrayToDataTable(arr);
    const options = {
      legend: {position: 'top'},
      hAxis: {
        title: 'Time after food in Minutes'
      },
      vAxis: {
        title: 'Sugar'
      },
      colors: ['#AB0D06'],
      curveType: 'function',
      trendlines: {
        1: {type: 'exponential', color: '#111', opacity: .3}
      },
      animation: {
        duration: 5000,
        startup:true,
        easing:"inAndOut"
      },
      chartArea: {
        left:20,
        top:0,
        height:'90%',
        width:'100%'
      },
      fontSize:14
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
