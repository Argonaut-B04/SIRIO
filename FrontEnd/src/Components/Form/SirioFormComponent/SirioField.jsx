import React, { Component } from 'react';
import classes from "./SirioField.module.css";
import SirioSelect from '../../Dropdown/SirioSelect';
import TextareaAutosize from 'react-textarea-autosize';

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
        } else if (this.props.type === "select") {
            return (
                <fieldset>
                    <label className={classes.label}>
                        {this.props.label}
                    </label>
                    <SirioSelect
                        name={this.props.name}
                        value={this.props.value}
                        handleChange={this.props.handleChange}
                        options={this.props.optionList}
                    />
                </fieldset>
            )
        } else if (this.props.type === "textarea") {
            return (
                <fieldset>
                    <label className={classes.label}>
                        {this.props.label}
                    </label>
                    <TextareaAutosize
                        name={this.props.name}
                        value={this.props.value}
                        onChange={this.props.handleChange}

                        placeholder={this.props.placeholder}
                        className={classes.input}
                        minRows={3}
                        maxRows={6}
                    />
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