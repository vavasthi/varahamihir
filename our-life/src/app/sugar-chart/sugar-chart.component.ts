import { AfterViewInit, Component, ElementRef, Input, ViewChild } from '@angular/core';
import { MatCardModule } from '@angular/material/card';
import { SugarReading } from '../pojo/sugar-reading';
import { ThemeSwitchComponent } from '../theme-switch/theme-switch.component';
import { ThemeSwitchService } from '../services/theme-switch.service';
declare var google: any;

@Component({
  selector: 'app-sugar-chart',
  standalone: true,
  imports: [MatCardModule, ThemeSwitchComponent],
  templateUrl: './sugar-chart.component.html',
  styleUrl: './sugar-chart.component.scss'
})
export class SugarChartComponent {

  private theme: string = 'light';
  private axisColor: string = 'white';
  private backgroundColor:string='transparent';
  private seriesColors= ['blue', '#AB0D06'];
  constructor(themeSwitchService: ThemeSwitchService) {
    themeSwitchService.getThemeChangeEvent().subscribe(theme => {
      this.theme = theme;
      if (this.theme == 'light') {
        this.axisColor = '#000000';
      }
      else {
        this.axisColor = '#FFFFFF';
        this.seriesColors = ['#F3E5F5', 'lightgreen', 'pink']
      }
      this.drawChart()
    })
  }
  @Input() sugarReadings?: SugarReading[];

  @ViewChild('lineChart') lineChart?: ElementRef

  drawChart = () => {

    if (this.sugarReadings != null) {

      const values = this.sugarReadings.map(sr => [sr.minutes, sr.reading])
      const arr = [['Minutes', 'Sugar'], ...values]
      const data = google.visualization.arrayToDataTable(arr);
      const options = {
        legend: { position: 'top' },
        hAxis: {
          title: 'Time after food in Minutes',
          titleTextStyle: { color: this.axisColor },
          baselineColor : this.axisColor,
          textPosition:'in',
          textStyle: { color : this.axisColor}
        },
        vAxis: {
          title: 'Sugar',
          titleTextStyle: { color: this.axisColor },
          baselineColor : this.axisColor,
          textPosition:'in',
          textStyle: { color : this.axisColor}
        },
        colors: this.seriesColors,
        curveType: 'function',
        trendlines: {
          1: { type: 'exponential', color: '#111', opacity: .3 }
        },
        animation: {
          duration: 5000,
          startup: true,
          easing: "inAndOut"
        },
        chartArea: {
          left: 20,
          top: 0,
          height: '90%',
          width: '100%'
        },
        backgroundColor: this.backgroundColor,
        fontSize: 14,
        lineWidth:5
      };

      const chart = new google.visualization.LineChart(this.lineChart?.nativeElement);

      chart.draw(data, options);
    }

  }

  ngAfterViewInit() {
    google.charts.load('current', { 'packages': ['corechart'] });
    google.charts.setOnLoadCallback(this.drawChart);
  }
}
