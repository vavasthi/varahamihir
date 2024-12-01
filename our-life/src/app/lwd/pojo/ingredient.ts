import {UnitConversion} from "./unit-conversion";
import {Quantity} from "./quantity";

export interface Ingredient {
  id?: string;
  name: string;
  description?: string;
  brand?:string;
  url: string;
  tags?: string[] | undefined;
  unitType:string;
  unitConversion?:UnitConversion;
}
