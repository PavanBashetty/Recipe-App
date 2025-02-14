import { Injectable } from "@angular/core";
import { BehaviorSubject } from "rxjs";

@Injectable({
    providedIn:'root'
})
export class customerIdShareObservable{

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

}