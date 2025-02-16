import { Injectable } from "@angular/core";
import { BehaviorSubject, Subject } from "rxjs";

@Injectable({
    providedIn:'root'
})
export class SharedService{

    /* SHARED SERVICE 1: To share customerId */
    private customerIdSubject = new BehaviorSubject<string | null>(localStorage.getItem('customerId'));
    customerId$ = this.customerIdSubject.asObservable();

    setCustomerId(customerId:string){
        localStorage.setItem('customerId',customerId);
        this.customerIdSubject.next(customerId);
    }

    getCustomerId():string | null{
        return this.customerIdSubject.getValue();
    }

    clearCustomerId(){
        localStorage.removeItem('customerId');
        this.customerIdSubject.next(null);
    }

    /* SHARED SERVICE 2: To trigger getAllRecipe() method from dashboard component */
    private refreshDashboardSubject = new Subject<void>();
    refreshDashboard$ = this.refreshDashboardSubject.asObservable();

    triggerDashboardRefresh(){
        this.refreshDashboardSubject.next();
    }


    /* SHARED SERVICE 3: To trigger getYourRecipes() method from ownRecipes component */
    private refreshOwnRecipesSubject = new Subject<void>();
    refreshOwnRecipe$ = this.refreshOwnRecipesSubject.asObservable();

    triggerOwnRecipeRefresh(){
        this.refreshOwnRecipesSubject.next();
    }

}