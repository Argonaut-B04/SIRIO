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

    generateField(key, customInput, type, index, name, value, handleChange, optionList, customClass, required, min, max, placeholder) {
        var field;
        if (customInput) {
            field = customInput;
        } else if (type === "select") {
            field =
                <SirioSelect
                    key={key}
                    index={index}
                    name={name}
                    value={value}
                    handleChange={handleChange}
                    options={optionList}
                    className={[customClass, classes.sirioSelect].join(" ")}
                />;
        } else if (type === "textarea") {
            field =
                <TextareaAutosize
                    key={key}
                    name={name}
                    value={value}
                    onChange={(event) => handleChange(event, index)}

                    placeholder={placeholder}
                    className={[customClass, classes.input].join(" ")}
                    minRows={3}
                    maxRows={6}

                    required={required}
                />
        } else if (type === "checkbox") {
            field =
                <input
                    key={key}
                    type={type}

                    name={name}
                    defaultChecked={value}
                    value={this.defaultChecked ? true : false}
                    onChange={handleChange}
                    index={index}

                    className={[customClass, classes.input].join(" ")}
                />
        } else {
            field =
                <input
                    key={key}
                    type={type}

                    name={name}
                    value={value}
                    onChange={(event) => handleChange(event, index)}

                    min={min}

                    placeholder={placeholder}
                    className={[customClass, classes.input].join(" ")}

                    required={required}
                />
        }
        return field;
    }

    generateAddButton(name, array, functionToUpdate) {
        return (
            <button
                type="button"
                onClick={
                    () => {
                        array.push("");
                        functionToUpdate(name, array);
                    }
                }>
                Tambah
            </button>
        )
    }

    generateSideDeleteButton(name, array, index, functionToUpdate) {
        if (array.length == 1) {
            return "";
        }
        return (
            <button
                type="button"
                onClick={
                    () => {
                        const newArray = array.slice(0, index).concat(array.slice(index + 1));
                        functionToUpdate(name, newArray);
                    }
                }>
                Hapus
            </button>
        )
    }

    render() {
        if (this.props.fullComponent) {
            return this.props.fullComponent;
        } else {
            const label = this.props.label &&
                <label className={classes.label}>
                    {this.props.label}
                    {this.props.validator && <p className={classes.error}>{this.props.validator}</p>}
                </label>
            // modifier untuk multiple
            var field;
            if (typeof this.props.value === "object") {
                const fieldList = [];
                var sidebutton = this.generateAddButton(this.props.name, this.props.value, this.props.modifier);
                for (let i = 0; i < this.props.value.length; i++) {
                    const deleteButton = this.generateSideDeleteButton(this.props.name, this.props.value, i, this.props.modifier);
                    const singleField =
                        this.generateField(
                            i,
                            this.props.customInput,
                            this.props.type,
                            this.props.index,
                            this.props.name,
                            this.props.value[i],
                            (event) => this.props.handleChange(event, i),
                            this.props.optionList,
                            this.props.customClass,
                            this.props.required,
                            this.props.min,
                            this.props.max,
                            this.props.placeholder
                        )

                    const fieldFinal =
                        <div className="row">
                            <div className="col-8">
                                {singleField}
                            </div>
                            <div className="col-2">
                                {deleteButton}
                            </div>
                            {i == (this.props.value.length - 1) &&
                                <div className="col-2">
                                    {sidebutton}
                                </div>
                            }
                        </div>

                    fieldList.push(fieldFinal)
                }
                field = <> {fieldList.map(aField => aField)} </>;
            } else {
                field = this.generateField(
                    0,
                    this.props.customInput,
                    this.props.type,
                    this.props.index,
                    this.props.name,
                    this.props.value,
                    this.props.handleChange,
                    this.props.optionList,
                    this.props.customClass,
                    this.props.required,
                    this.props.min,
                    this.props.max,
                    this.props.placeholder
                )
            }

            return (
                <fieldset>
                    <div className="row">
                        <div className="col-3">
                            {label}
                        </div>
                        <div className={this.props.sideButton ? "col-8" : "col-9"}>
                            {field}
                        </div>
                        {this.props.sideButton &&
                            <div className="col-1">
                                {this.props.sideButton}
                            </div>
                        }
                    </div>
                </fieldset>
            )
        }
    }
}