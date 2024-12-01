import { Quantity } from "./quantity"
import {Unit} from "./unit";

export interface UnitConversion {
    weightQuantity:number;
    weightUnit:string;
    volumeQuantity:number;
    volumeUnit:string;
}


