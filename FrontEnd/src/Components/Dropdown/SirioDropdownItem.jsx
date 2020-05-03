import React, { Component } from 'react';
import classes from './SirioDropdownItem.module.css';

/**
 * Komponen Item dari SirioDropdown
 * Masukan sebagai children dari SirioDropdown
 * 
 * Props yang tersedia:
 * - classes            : cssClass, custom class untuk item
 * - clickArgument      : event, argumen yang digunakan untuk fungsi onClick
 * - onClick            : function, dijalankan ketika item dropdown dipilih
 */
export default class SirioDropdownItem extends Component {
    render() {
        const { clickArgument, children, onClick } = this.props;
        return (
            <div className={[this.props.classes, classes.item].join(" ")} onClick={() => onClick(clickArgument)} >
                {children}
            </div>
        );
    }
}