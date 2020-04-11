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
        return (
            <>
                {this.props.noHeader || this.props.customHeader ? this.props.customHeader :
                    <SirioComponentHeader
                        title={this.props.title}
                        subtitle={this.props.subtitle}
                        betweenTitleSubtitle={this.props.betweenTitleSubtitle}
                    />
                }
                {this.props.isInnerForm || this.props.childForm ?
                    <ComponentWrapper classes={this.props.isInnerForm ? "m-4" : ""}>
                        {this.props.inputDefinition.map((field, i) =>
                            <SirioField
                                key={i}
                                label={field.label}
                                handleChange={(event) => field.handleChange(event, this.props.id)}
                                type={field.type}
                                name={field.name}
                                value={field.value}
                                placeholder={field.placeholder}
                                optionList={field.optionList}
                                classes={field.classes}
                                customInput={field.customInput}
                                fullComponent={field.fullComponent}
                            />
                        )}
                        {this.props.footerButton ?
                            <div className="w-100 text-right">
                                <br />
                                {this.props.footerButton}
                            </div>
                            : ""
                        }
                    </ComponentWrapper>
                    :
                    <ComponentWrapper>
                        <form onSubmit={this.props.onSubmit}>
                            {this.props.inputDefinition.map((field, i) =>
                                <SirioField
                                    key={i}
                                    label={field.label}
                                    handleChange={(event) => field.handleChange(event, this.props.id)}
                                    type={field.type}
                                    name={field.name}
                                    value={field.value}
                                    placeholder={field.placeholder}
                                    optionList={field.optionList}
                                    classes={field.classes}
                                    customInput={field.customInput}
                                    fullComponent={field.fullComponent}
                                />
                            )}
                            <div className="w-100 text-right">
                                <br></br>
                                {this.props.submitButton}
                            </div>
                        </form>
                    </ComponentWrapper>
                }
            </>
        );
    }
}

export default SirioForm;
