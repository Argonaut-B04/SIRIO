import React, { Component } from 'react';
import SirioField from './SirioFormComponent/SirioField';
import SirioComponentHeader from '../Header/SirioComponentHeader';
import ComponentWrapper from '../../Layout/ComponentWrapper';

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
        var header;
        if (this.props.noHeader || this.props.customHeader) {
            header = this.props.customHeader
        } else {
            header = <SirioComponentHeader
                title={this.props.title}
                subtitle={this.props.subtitle}
                betweenTitleSubtitle={this.props.betweenTitleSubtitle}
            />
        }

        var fields =
            <>
                {this.props.inputDefinition.map((field, i) =>
                    <SirioField
                        key={i}
                        label={field.label}
                        handleChange={field.handleChange}
                        index={field.index}
                        type={field.type}
                        name={field.name}
                        value={field.value}
                        placeholder={field.placeholder}
                        optionList={field.optionList}
                        classes={field.classes}
                        customInput={field.customInput}
                        fullComponent={field.fullComponent}
                        validator={field.validation}
                        required={field.required}
                        min={field.min}
                        afterValidity={field.afterValidity}
                        multiple={field.multiple}
                        sideButton={field.sideButton}
                        modifier={field.modifier}
                        validationFunction={field.validationFunction}
                    />
                )}
            </>

        if (this.props.isInnerForm || this.props.childForm) {
            return (
                <>
                    {header}
                    <ComponentWrapper classes="m-4">
                        {fields}

                        {this.props.footerButton &&
                            <div className="w-100 text-right">
                                <br />
                                {this.props.footerButton}
                            </div>
                        }
                    </ComponentWrapper>
                </>
            )
        } else {
            return (
                <>
                    {header}
                    <ComponentWrapper>
                        <form onSubmit={this.props.onSubmit}>
                            {fields}
                            <div className="w-100 text-right mt-5">
                                {this.props.submitButton}
                            </div>
                        </form>
                    </ComponentWrapper>
                </>
            )
        }

    }
}
export default SirioForm;