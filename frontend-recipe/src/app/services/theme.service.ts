import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class ThemeService {

  private readonly THEME_KEY = "user-theme";

  constructor() { }

  setTheme(isDark:boolean){
    if(isDark){
      document.body.classList.add('dark-theme');
      document.body.classList.remove('light-theme');
      localStorage.setItem(this.THEME_KEY,'dark');
    }else{
      document.body.classList.add('light-theme');
      document.body.classList.remove('dark-theme');
      localStorage.setItem(this.THEME_KEY,'light');
    }
  }

  loadTheme(){
    const savedTheme = localStorage.getItem(this.THEME_KEY);
    this.setTheme(savedTheme === 'dark')
  }

  toggleTheme(){
    const isDark = document.body.classList.contains('dark-theme');
    this.setTheme(!isDark);
  }
}
