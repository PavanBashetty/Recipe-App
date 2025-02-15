import { Injectable } from "@angular/core";
import { BehaviorSubject, Subject } from "rxjs";

@Injectable({
    providedIn:'root'
})
export class SharedService{

    /* SHARED SERVICE 1: To share customerId */
    private customerIdSubject = new BehaviorSubject<string | null>(sessionStorage.getItem('customerId'));
    customerId$ = this.customerIdSubject.asObservable();

    setCustomerId(customerId:string){
        sessionStorage.setItem('customerId',customerId);
        this.customerIdSubject.next(customerId);
    }

    getCustomerId():string | null{
        return this.customerIdSubject.getValue();
    }

    clearCustomerId(){
        sessionStorage.removeItem('customerId');
        this.customerIdSubject.next(null);
    }

    /* SHARED SERVICE 2: To trigger getAllRecipe() method from dashboard component */
    private refreshDashboardSubject = new Subject<void>();
    refreshDashboard$ = this.refreshDashboardSubject.asObservable();

    triggerRefresh(){
        this.refreshDashboardSubject.next();
    }

}