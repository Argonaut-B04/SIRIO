import React, { Component } from 'react';

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
        return (
            <div className={this.props.classes} onClick={() => this.props.onClick(this.props.clickArgument)} >
                {this.props.children}
            </div>
        );
    }
}