import { Direction } from '../enums/direction.enum';

export interface Sort {
    readonly field: string;
    readonly direction: Direction;
    readonly date: boolean;
}
