import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {DomSanitizer} from "@angular/platform-browser";
import {NotificationService} from "../../../modules/notification/services/notification.service";
import {UserEventService} from "../../../services/userEvent.service";
import {MUserEvent} from "../../../models/MUserEvent";

@Component({
    selector: 'app-createUserEventExpense',
    templateUrl: './createUserEventExpense.component.html',
    styleUrls: ['./createUserEventExpense.component.css']
})
export class CreateUserEventExpenseComponent implements OnInit {
    _userEvent: MUserEvent;
    @Output()
    eventSave = new EventEmitter<boolean>();
    operation: string[] = [];
    showOperation: string ="";
    totalExpense: number;

    constructor(private userEventService: UserEventService,
                private sanitizer: DomSanitizer,
                private notificationService: NotificationService
    ) {
    }

    ngOnInit() {}

    get userEvent(){
        return this._userEvent;
    }

    @Input() set userEvent(userEvent: MUserEvent){
        if (userEvent != undefined){
            this._userEvent = userEvent;
            this.totalExpense = this.userEvent.totalExpense;
            this.operation = [];
            this.operation[0] = userEvent.totalExpense.toString();
            this.showOperation = userEvent.totalExpense.toString();
        }else{
            this._userEvent = new MUserEvent();
            this.totalExpense = 0;
            this.operation = [];
            this.showOperation = "";
        }
    }

    onEdit(){
        this.userEventService.editTotalExpense(this.userEvent).subscribe(() => {
            this.eventSave.emit();
            this.closeModal();
            document.getElementById("closeButton").click();
            this.notificationService.success("Dinero añadido al evento", "Se ha editado el dinero añadido correctamente.");
        });
    }

    calculator(value: number, operator: string){
        if(value != -1){
            if (this.operation[0] != null && this.operation[1] != null){
                if (this.operation[2] == null){
                    this.operation[2] = value.toString();
                }else{
                    this.operation[2] += value.toString();
                }
                this.showOperation += value.toString();
            }
        }else{
            if (operator == "R"){
                this.operation = [];
                this.operation[0] = this.totalExpense.toString();
                this.showOperation = this.totalExpense.toString();
                this.userEvent.totalExpense = this.totalExpense;
            }else if (operator == "C") {
                this.operation = [];
                this.operation[0] = this.userEvent.totalExpense.toString();
                this.showOperation = this.userEvent.totalExpense.toString();
            }else{
                    if (this.operation[0] != null){
                        if (this.operation[2] != null){
                            if (operator == "."){
                                this.operation[2] += operator.toString();
                                this.showOperation += operator;
                            }else if (operator == "="){
                                this.showOperation += operator;
                                this.operation[3] = operator;
                                if (this.operation[1].toString() == "+"){
                                    this.userEvent.totalExpense = parseFloat((parseFloat(this.operation[0]) + parseFloat(this.operation[2])).toFixed(2));
                                }else if (this.operation[1].toString() == "-"){
                                    this.userEvent.totalExpense = parseFloat((parseFloat(this.operation[0]) - parseFloat(this.operation[2])).toFixed(2));
                                }else if (this.operation[1].toString() == "/"){
                                    this.userEvent.totalExpense = parseFloat((parseFloat(this.operation[0]) / parseFloat(this.operation[2])).toFixed(2));
                                }else if (this.operation[1].toString() == "*"){
                                    this.userEvent.totalExpense = parseFloat((parseFloat(this.operation[0]) * parseFloat(this.operation[2])).toFixed(2));
                                }
                                this.operation = [];
                                this.operation[0] = this.userEvent.totalExpense.toString();
                                this.showOperation = this.userEvent.totalExpense.toString();
                            }
                        }else if(this.operation[1] == null && operator != "." && operator != "="){
                            this.showOperation += operator;
                            this.operation[1] = operator;
                        }
                    }
                }
        }
    }

    closeModal(){
        this.operation = [];
        this.operation[0] = this.totalExpense.toString();
        this.showOperation = this.totalExpense.toString();
        this.userEvent.totalExpense = this.totalExpense;

    }
}