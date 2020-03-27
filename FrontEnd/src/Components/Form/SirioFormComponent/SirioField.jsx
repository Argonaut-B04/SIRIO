import React, { Component } from 'react';
import classes from "./SirioField.module.css";

/**
 * Komponen field untuk SirioForm
 * 
 * Props yang tersedia:
 * - label              : String, judul field
 * - customInput        : Komponen, jika ingin menggunakan selain input tag
 * - type               : String, tipe input tag
 * - name               : String, nama state yang akan dikaitkan dengan field ini
 * - value              : String, nilai dari name
 * - onChange           : Function, fungsi yang akan dijalankan ketika field diubah
 * - placeholder        : String, placeholder dari input tag
 */
export default class SirioField extends Component {


    render() {
        if (this.props.customInput) {
            return (
                <fieldset>
                    <label className={classes.label}>
                        {this.props.label}
                    </label>
                    {this.props.customInput}
                </fieldset>
            )
        }
        return (
            <fieldset>
                <label className={classes.label}>
                    {this.props.label}
                </label>
                <input
                    type={this.props.type}

                    name={this.props.name}
                    value={this.props.value}
                    onChange={this.props.handleChange}

                    placeholder={this.props.placeholder}
                    className={classes.input}
                />
            </fieldset>
        );
    }
}