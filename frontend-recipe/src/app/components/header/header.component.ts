import { Component } from '@angular/core';
import {MatIconModule} from '@angular/material/icon';
import {MatButtonModule} from '@angular/material/button';
import {MatToolbarModule} from '@angular/material/toolbar';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatListModule } from '@angular/material/list';
import { Router, RouterOutlet, RouterLink, RouterLinkActive } from '@angular/router';
import { ThemeService } from '../../services/theme.service';
import { SharedService } from '../../services/shared.service';

@Component({
  selector: 'app-header',
  imports: [MatIconModule, MatButtonModule, MatToolbarModule, MatSidenavModule, MatListModule, RouterOutlet, RouterLink, RouterLinkActive],
  templateUrl: './header.component.html',
  styleUrl: './header.component.scss'
})
export class HeaderComponent {

  isDarkMode:string = '';
  constructor(private themeService:ThemeService, private router:Router, private custIdService:SharedService){}


  ngOnInit(){
    this.themeService.loadTheme();
  }

  toggleTheme(){
    this.themeService.toggleTheme();
  }
  logout(){
    this.custIdService.clearCustomerId();
    localStorage.removeItem('user-theme');
    localStorage.removeItem('jwtToken');
    this.router.navigate(['/login']);
  }
}
