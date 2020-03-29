import React, { Component } from 'react';
import SirioField from './SirioFormComponent/SirioField';
import classes from "./SirioForm.module.css";

/**
 * Komponen form untuk Sirio secara umum
 * 
 * Props yang tersedia:
 * - title              : String, Judul Form
 * - headerButton       : Komponen, yang akan ditampilkan di sisi sebelah kanan title
 * - inputDefinition    : List of JSON, definisi dari tiap field. See also SirioField.
 * - onSubmit           : Function, fungsi yang akan dijalankan ketika user submit form. 
 * - submitButton       : Komponen, button submit. Jika tidak disediakan, akan menggunakan tombol default
 */
class SirioForm extends Component {

    render() {
        return (
            <>
                <div className={classes.headerWrapper}>
                    <h2 className={classes.title}>
                        {this.props.title}
                    </h2>
                </div>
                <form className={classes.formWrapper} onSubmit={this.props.onSubmit}>
                    {this.props.inputDefinition.map((field, i) =>
                        <SirioField
                            key={i}
                            label={field.label}
                            handleChange={field.handleChange}
                            type={field.type}
                            name={field.name}
                            value={field.value}
                            placeholder={field.placeholder}
                            optionList={field.optionList}
                        />
                    )}
                    <div className="w-100 text-right">
                        <br></br>
                        {this.props.submitButton}
                    </div>
                </form>
            </>
        );
    }
}

export default SirioForm;
