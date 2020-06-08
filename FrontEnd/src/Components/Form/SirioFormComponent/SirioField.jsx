import React, { Component } from 'react';
import classes from "./SirioField.module.css";
import SirioSelect from '../../Dropdown/SirioSelect';
import TextareaAutosize from 'react-textarea-autosize';
import SirioRectangularButton from '../../Button/SirioRectangularButton';

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

    constructor(props) {
        super(props)
        this.state = {}
    }

    // Ini fungsi untuk mengetes apakah parameter (functionToCheck) adalah fungsi
    // nanti dipindahkan ke SirioAxiosBase
    isFunction(functionToCheck) {
        return functionToCheck && {}.toString.call(functionToCheck) === '[object Function]';
    }

    // Sesegera mungkin, shouldComponentUpdate harus dibuka (tapi gak sekarang juga);
    shouldComponentUpdate(nextprops, nextstate) {

        if (this.props.type === "select" || this.props.type === "date") {
            return true;
        };

        if (this.props.disabled !== nextprops.disabled) {
            return true;
        }

        // untuk form biasa
        if (typeof nextprops.value !== "undefined") {
            if (this.props.errormessage !== nextprops.errormessage) {
                return true;
            }
            if (this.props.value === nextprops.value) {
                return false;
            }
        }

        return true;
    }

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
                    placeholder={placeholder}
                    options={optionList}
                    required={required}
                    className={[customClass, classes.sirioSelect].join(" ")}
                    disabled={this.props.disabled}
                />;
        } else if (type === "textarea") {
            field =
                <TextareaAutosize
                    key={key}
                    name={name}
                    value={value}
                    onChange={(event) => handleChange(event, index)}

                    disabled={this.props.disabled}

                    placeholder={placeholder}
                    className={[customClass, classes.input].join(" ")}
                    minRows={6}
                    maxRows={12}

                    required={required}
                />
        } else if (type === "checkbox") {
            if (this.props.value) {
                field =
                    <input
                        type={this.props.type}

                        disabled={this.props.disabled}

                        checked
                        
                        name={this.props.name}
                        defaultChecked={this.props.value}
                        value={this.defaultChecked ? true : false}
                        onChange={this.props.handleChange}
                        index={this.props.index}

                        className={this.props.classes ? [this.props.classes, classes.input].join(" ") : classes.input}
                    />
            } else {
                field =
                    <input
                        type={this.props.type}

                        disabled={this.props.disabled}

                        name={this.props.name}
                        defaultChecked={this.props.value}
                        value={this.defaultChecked ? true : false}
                        onChange={this.props.handleChange}
                        index={this.props.index}

                        className={this.props.classes ? [this.props.classes, classes.input].join(" ") : classes.input}
                    />
            }
        } else {
            field =
                <input
                    key={key}
                    type={type}

                    name={name}
                    value={value}
                    onChange={(event) => handleChange(event, index)}

                    disabled={this.props.disabled}

                    min={min}
                    max={max}

                    placeholder={placeholder}
                    className={[customClass, classes.input].join(" ")}

                    required={required}
                />
        }
        return field;
    }

    generateAddButton(name, array, functionToUpdate) {
        return (
            <SirioRectangularButton
                type="add"
                onClick={
                    () => {
                        array.push("");
                        functionToUpdate(name, array);
                    }
                }
            />
        )
    }

    generateSideDeleteButton(name, array, index, functionToUpdate) {
        if (array.length === 1) {
            return "";
        }
        return (
            <SirioRectangularButton
                type="delete"
                onClick={
                    () => {
                        const newArray = array.slice(0, index).concat(array.slice(index + 1));
                        functionToUpdate(name, newArray, index);
                    }
                }
            />
        )
    }

    render() {
        if (this.props.fullComponent) {
            return this.props.fullComponent;
        } else {
            const label = this.props.label &&
                <>
                    <label className={this.props.required ? [classes.label, classes.required].join(" ") : classes.label}>
                        {this.props.label}
                    </label>

                    {/* (validasi metode 2 yang gagal) untuk sementara, error dari validator belum aku hapus */}
                    {this.props.validator && <p className={classes.error}>{this.props.validator}</p>}

                    {/* validasi metode 3, ini yang paling berhasil menurutku */}
                    {/* Ini errormessage yang kita kirim dari inputdefinition */}
                    {/* jadi SirioField tugasnya hanya full menampilkan */}
                    {this.props.errormessage && <p className={classes.error}>{this.props.errormessage}</p>}

                    {/* ini validasi metode 1, karena Greta dan Modi masih pake metode 1, kita biarkan saja dulu */}
                    {this.state.validationResult && <p className={classes.error}>{this.state.validationResult}</p>}
                </>
            // modifier untuk multiple
            var field;
            if (Array.isArray(this.props.value)) {
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
                        <div className="row" key={i}>
                            <div className="col-10">
                                {singleField}
                            </div>
                            <div className={["col-2", classes.buttonWrapper].join(" ")}>
                                {deleteButton}
                                {i === (this.props.value.length - 1) ? sidebutton : ""}
                                {this.props.sideButton}
                            </div>
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
                    {
                        label
                            ?
                            <div className="row">
                                <div className="col-3">
                                    {label}
                                </div>
                                <div className={"col-9"}>
                                    {field}
                                </div>
                            </div>
                            :
                        field
                    }
                </fieldset>
            )
        }
    }
}