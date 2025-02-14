import { Component } from '@angular/core';
import {MatIconModule} from '@angular/material/icon';
import {MatButtonModule} from '@angular/material/button';
import {MatToolbarModule} from '@angular/material/toolbar';
import { Router, RouterOutlet } from '@angular/router';
import { ThemeService } from '../../services/theme.service';

@Component({
  selector: 'app-header',
  imports: [MatIconModule, MatButtonModule, MatToolbarModule, RouterOutlet],
  templateUrl: './header.component.html',
  styleUrl: './header.component.scss'
})
export class HeaderComponent {

  isDarkMode:string = '';
  constructor(private themeService:ThemeService, private router:Router){}


  ngOnInit(){
    this.themeService.loadTheme();
  }

  toggleTheme(){
    this.themeService.toggleTheme();
  }
  logout(){
    localStorage.removeItem('jwtToken');
    this.router.navigate(['/login']);
  }
}
