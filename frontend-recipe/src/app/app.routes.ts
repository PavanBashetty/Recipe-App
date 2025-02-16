import { Routes } from '@angular/router';
import { LoginComponent } from './components/login/login.component';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { AboutComponent } from './components/about/about.component';
import { HeaderComponent } from './components/header/header.component';
import { RegisterComponent } from './components/register/register.component';
import { authGuard } from './_guards/auth.guard';
import { loginGuard } from './_guards/login.guard';
import { OwnRecipesComponent } from './components/own-recipes/own-recipes.component';

export const routes: Routes = [
    {path:'login',component:LoginComponent, canActivate:[loginGuard]},
    {path:'register',component:RegisterComponent, canActivate:[loginGuard]},
    {
        path:'',
        component:HeaderComponent,
        canActivate:[authGuard],
        children:[
            {path:'',redirectTo:'dashboard',pathMatch:'full'},
            {path:'dashboard',component:DashboardComponent},
            {path:'ownrecipes',component:OwnRecipesComponent},
            {path:'about',component:AboutComponent}
        ]
    },
    {path:'**',redirectTo:'login'}
];
