import React from 'react';
import SirioForm from '../SirioForm';
import SirioButton from '../../Button/SirioButton';
import HasilPemeriksaanService from '../../../Services/HasilPemeriksaanService';
import RisikoService from '../../../Services/RisikoService';
import { Redirect } from 'react-router-dom';
import { withRouter } from 'react-router-dom';
import RiskLevelService from "../../../Services/RiskLevelService";
import SirioField from "../SirioFormComponent/SirioField";

class HasilPemeriksaanFormTambah extends React.Component {

    // Masukan user disimpan kedalam state sebelum dikirim ke backend
    constructor(props) {
        super(props);

        this.state = {
            daftarKomponenPemeriksaan: [],
            daftarRisikoKategori1: [],
            daftarRisikoKategori2: [],
            filterKategori: "",
            kategoriType: "",
            riskLevelOptionList: [],
            riskOptionList: [],
            submitableDraft: false,
            submitable: false,
            redirect: false
        };

        this.renderRisikoKategori12 = this.renderRisikoKategori12.bind(this);
        this.renderRisikoKategori3 = this.renderRisikoKategori3.bind(this);
        this.renderRiskLevelOption = this.renderRiskLevelOption.bind(this);
        this.handleChangeKomponen = this.handleChangeKomponen.bind(this);
        this.handleSelectChangeKomponen = this.handleSelectChangeKomponen.bind(this);
        this.handleSelectChange = this.handleSelectChange.bind(this);
        this.handleSelectChangeRisiko = this.handleSelectChangeRisiko.bind(this);
        this.handleMultiFieldChange = this.handleMultiFieldChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
        this.modifyFieldCount = this.modifyFieldCount.bind(this);
        this.setRedirect = this.setRedirect.bind(this);
        this.innerInputDefinition = this.innerInputDefinition.bind(this);
    }

    componentDidMount() {
        this.renderRisikoKategori12();
        this.renderRisikoKategori3();
        this.renderRiskLevelOption();
    }

    componentDidUpdate(prevProps, prevState) {
        var submitable = true;
        var submitableDraft = true;

        submitable = this.validateRequired();
        prevState.daftarKomponenPemeriksaan.map((prevKomponen, index) => {
            const validation = this.validateKeteranganSampel();
            submitable = submitable && validation;
            submitableDraft = submitableDraft && validation;
        });

        if (this.state.submitable !== submitable) {
            this.setState({
                submitable: submitable
            })
        }

        if (this.state.submitableDraft !== submitableDraft) {
            this.setState({
                submitableDraft: submitableDraft
            })
        }
    }

    setRedirect = () => {
        this.setState({
            redirect: true
        })
    };

    renderRedirect = () => {
        if (this.state.redirect) {
            return <Redirect to={{
                pathname: "/hasil-pemeriksaan",
                state: {
                    addSuccess: true
                }
            }} />
        }
    };

    async renderRiskLevelOption() {
        const response = await RiskLevelService.getAll();

        const riskLevelList = response.data.result.map(riskLevel => {
            return (
                {
                    label: riskLevel.namaLevel,
                    value: riskLevel.idLevel
                }
            )
        });

        this.setState({
            riskLevelOptionList: riskLevelList
        })
    }

    async renderRisikoKategori12() {
        const response = await RisikoService.getAll();

        const risikoKategori1 = response.data.result
            .filter(risiko => risiko.risikoKategori === 1)
            .map(risiko => {
                return (
                    {
                        label: risiko.namaRisiko,
                        value: risiko.idRisiko
                    }
                )
            });

        const risikoKategori2 = response.data.result
            .filter(risiko => risiko.risikoKategori === 2)
            .map(risiko => {
                return (
                    {
                        label: risiko.namaRisiko,
                        value: risiko.idRisiko
                    }
                )
            });

        this.setState({
            daftarRisikoKategori1: risikoKategori1,
            daftarRisikoKategori2: risikoKategori2
        })
    }

    async renderRisikoKategori3() {
        const response = await RisikoService.getAllChild();

        const daftarKomponen = response.data.result
            .map(risiko => {
                return (
                    {
                        id: risiko.id,
                        idRiskLevel: "",
                        errorIdRiskLevel: "",
                        risiko:risiko,
                        jumlahSampel:"",
                        errorJumlahSampel:"",
                        keteranganSampel:"",
                        errorKeteranganSampel:"",
                        daftarTemuanRisiko:[
                            {
                                keterangan: ""
                            }
                        ],
                        daftarRekomendasi:[
                            {
                                keterangan: ""
                            }
                        ]
                    }
                )
            });

        this.setState({
            daftarKomponenPemeriksaan: daftarKomponen
        })
    }

    getSOPButton(namaSop, linkSop) {
        return (
            <SirioButton
                purple
                hyperlinkLeft
                onClick={() => window.location.href = linkSop}
            >
                {namaSop}
            </SirioButton>
        )
    }

    innerInputDefinition(komponen) {
        return (
            [
                {
                    label: "Komponen Risiko",
                    customInput: komponen.risiko.nama
                }, {
                    label: "SOP",
                    customInput: this.getSOPButton(komponen.risiko.namaSop, komponen.risiko.linkSop)
                }, {
                    label: "Jumlah Sampel",
                    handleChange: (event) => this.handleChangeKomponen(event, komponen.id),
                    type: "number",
                    name: "jumlahSampel",
                    min: 0,
                    value: komponen.jumlahSampel,
                    placeholder: "0",
                    validation: komponen.errorJumlahSampel
                }, {
                    label: "Keterangan Sampel",
                    handleChange: (event) => this.handleChangeKomponen(event, komponen.id),
                    type: "textarea",
                    name: "keteranganSampel",
                    value: komponen.keteranganSampel,
                    placeholder: "Keterangan sampel",
                    validation: komponen.errorKeteranganSampel
                }, {
                    label: "Risk Level",
                    handleChange: (name, event) => this.handleSelectChangeKomponen(name, event, komponen.id),
                    type: "select",
                    name: "idRiskLevel",
                    value: komponen.idRiskLevel,
                    optionList: this.state.riskLevelOptionList
                }, {
                    label: "Hasil Temuan",
                    multiple: true,
                    isMultipleObject: true,
                    handleChange: (event, index) => this.handleMultiFieldChange(event, index, komponen.id, komponen.daftarTemuanRisiko, "keterangan"),
                    type: "textArea",
                    name: "daftarTemuanRisiko",
                    value: komponen.daftarTemuanRisiko.map(temuan => temuan.keterangan),
                    modifier: (name, newField) => this.modifyFieldCount(name, newField, komponen.id, "keterangan"),
                }, {
                    label: "Rekomendasi",
                    multiple: true,
                    isMultipleObject: true,
                    handleChange: (event, index) => this.handleMultiFieldChange(event, index, komponen.id, komponen.daftarRekomendasi, "keterangan"),
                    type: "textArea",
                    name: "daftarRekomendasi",
                    value: komponen.daftarRekomendasi.map(rekomendasi => rekomendasi.keterangan),
                    modifier: (name, newField) => this.modifyFieldCount(name, newField, komponen.id, "keterangan"),
                }
            ]
        )
    }

    outerInputDefinition() {
        if(this.state.daftarKomponenPemeriksaan.length > 0) {
            if (this.state.kategoriType === "1") {
                return (
                    this.state.daftarKomponenPemeriksaan
                        .filter(komponen => komponen.risiko.grantParent === this.state.filterKategori)
                        .map(komponen => {
                            return (
                                {
                                    fullComponent:
                                        <SirioForm
                                            noHeader
                                            isInnerForm
                                            inputDefinition={this.innerInputDefinition(komponen)}
                                        />
                                }
                            )
                        })
                )
            } else if (this.state.kategoriType === "2") {
                return (
                    this.state.daftarKomponenPemeriksaan
                        .filter(komponen => komponen.risiko.parent === this.state.filterKategori)
                        .map(komponen => {
                            return (
                                {
                                    fullComponent:
                                        <SirioForm
                                            noHeader
                                            isInnerForm
                                            inputDefinition={this.innerInputDefinition(komponen)}
                                        />
                                }
                            )
                        })
                )
            } else {
                return (
                    this.state.daftarKomponenPemeriksaan
                        .map(komponen => {
                            return (
                                {
                                    fullComponent:
                                        <SirioForm
                                            noHeader
                                            isInnerForm
                                            inputDefinition={this.innerInputDefinition(komponen)}
                                        />
                                }
                            )
                        })
                )
            }
        } else {
            return (
                [
                    {
                        label: "Loading",
                        customField: <p>Loading...</p>
                    }
                ]
            )
        }
    }

    getBetween() {
        return (
            <>
                <div className="col-md-3 pl-0">
                    <SirioField
                        type="select"
                        handleChange={this.handleSelectChangeRisiko}
                        classes="p-1"
                        name="kategoriType"
                        value={this.state.kategoriType}
                        optionList={
                            [
                                {
                                    label: "Semua",
                                    value: "0"
                                },
                                {
                                    label: "Kategori 1",
                                    value: "1"
                                },
                                {
                                    label: "Kategori 2",
                                    value: "2"
                                }
                            ]
                        }
                    />
                </div>
                <div className="col-md-3 pl-0">
                    <SirioField
                        type="select"
                        handleChange={this.handleSelectChange}
                        classes="p-1"
                        name="filterKategori"
                        value={this.state.filterKategori}
                        optionList={this.state.riskOptionList}
                    />
                </div>
            </>
        )
    }

    modifyFieldCount(name, newField, idKomponen, objectKey) {
        const newArray = [];
        for(let i = 0; i < newField.length; i++) {
            newArray.push({
                [objectKey]: newField[i]
            })
        }
        this.setState(prevState => ({
            ...prevState,
            daftarKomponenPemeriksaan: prevState.daftarKomponenPemeriksaan.map(komponen => ({
                ...komponen,
                [name]: komponen.id === idKomponen ? newArray : komponen[name]
            }))
        }))
    }

    handleMultiFieldChange(event, index, idKomponen, array, objectKey) {
        const targetName = event.target.name;
        const targetValue = event.target.value;
        const targetArray = array;
        targetArray[index] = {
            [objectKey]: targetValue
        };
        this.setState(prevState => ({
            ...prevState,
            daftarKomponenPemeriksaan: prevState.daftarKomponenPemeriksaan.map(komponen => ({
                ...komponen,
                [targetName]: komponen.id === idKomponen ? targetArray : komponen[targetName]
            }))
        }))
    }

    handleChangeKomponen(event, idKomponen) {
        const { name, value } = event.target;

        this.setState(prevState => ({
            ...prevState,
            daftarKomponenPemeriksaan: prevState.daftarKomponenPemeriksaan.map(komponen => ({
                ...komponen,
                [name]: komponen.id === idKomponen ? value : komponen[name]
            }))
        }))
    }

    handleSelectChangeKomponen(name, event, idKomponen) {
        this.setState(prevState => ({
            ...prevState,
            daftarKomponenPemeriksaan: prevState.daftarKomponenPemeriksaan.map(komponen => ({
                ...komponen,
                [name]: komponen.id === idKomponen ? event.value : komponen[name]
            }))
        }));
    }

    handleSelectChangeRisiko(name, event) {
        const risiko1 = this.state.daftarRisikoKategori1;
        const risiko2 = this.state.daftarRisikoKategori2;
        if (event.value === "1") {
            this.setState(
                {
                    [name]
                        : event.value,
                    riskOptionList: risiko1,
                    filterKategori: risiko1[0].value
                }
            )
        } else if (event.value === "2") {
            this.setState(
                {
                    [name]
                        : event.value,
                    riskOptionList: risiko2,
                    filterKategori: risiko2[0].value
                }
            )
        } else {
            this.setState(
                {
                    [name]
                        : event.value,
                    riskOptionList: [
                        {
                            label: "Semua",
                            value: "-1"
                        }
                    ],
                    filterKategori: "-1"
                }
            )
        }
    }

    handleSelectChange(name, event) {
        this.setState(
            {
                [name]
                    : event.value,
            }
        )
    }

    handleSubmit(event, status) {
        event.preventDefault();
        const hasilPemeriksaan = {
            idStatus: status,
            tugasPemeriksaan:{id: this.props.location.state.id},
            daftarKomponenPemeriksaan: this.state.daftarKomponenPemeriksaan.map(komponen => {
                return (
                    {
                        idRiskLevel: komponen.idRiskLevel,
                        risiko: komponen.risiko,
                        jumlahSampel: komponen.jumlahSampel,
                        keteranganSampel: komponen.keteranganSampel,
                        daftarTemuanRisiko: komponen.daftarTemuanRisiko,
                        daftarRekomendasi: komponen.daftarRekomendasi
                    }
                )
            })
        };
        if ((status === 1 && this.state.submitableDraft) || (status === 2 && this.state.submitable)) {
            HasilPemeriksaanService.addHasilPemeriksaan(hasilPemeriksaan)
                .then(() => this.setRedirect());
        }
    }

    validateRequired() {
        var submitable = true;
        this.state.daftarKomponenPemeriksaan.map(komponen => {
            submitable = submitable &&
                (komponen.jumlahSampel !== null && komponen.jumlahSampel !== "") &&
                (komponen.keteranganSampel !== null && komponen.keteranganSampel !== "") &&
                (komponen.idRiskLevel !== null && komponen.idRiskLevel !== "");
            return null
        });
        return submitable;
    }

    validateKeteranganSampel() {
        var submitable = true;
        this.state.daftarKomponenPemeriksaan.map((komponen, index) => {
            const fokusKeteranganSampel = komponen.keteranganSampel;
            var errorKeteranganSampel;

            if (!isNaN(fokusKeteranganSampel) && fokusKeteranganSampel !== "") {
                submitable = false;
                errorKeteranganSampel = "Ketarangan perlu mengandung huruf";
            }
            if (komponen.errorKeteranganSampel !== errorKeteranganSampel) {
                this.setState(prevState => ({
                    ...prevState,
                    daftarKomponenPemeriksaan: prevState.daftarKomponenPemeriksaan.map(komponenState => ({
                        ...komponenState,
                        errorKeteranganSampel: komponenState.id === komponen.id ? errorKeteranganSampel : komponenState.errorKeteranganSampel
                    }))
                }))
            } return null
        });
        return submitable;
    }

    // validateJumlahSampel() {
    //     var submitable = true;
    //     this.state.daftarKomponenPemeriksaan.map((komponen, index) => {
    //         const fokusJumlahSampel = komponen.jumlahSampel;
    //         var errorJumlahSampel;
    //
    //         console.log("yey")
    //         if (isNaN(parseFloat(fokusJumlahSampel)) && fokusJumlahSampel !== "") {
    //             console.log("masukEu")
    //             submitable = false;
    //             errorJumlahSampel = "Jumlah harus angka";
    //         }
    //         if (komponen.errorJumlahSampel !== errorJumlahSampel) {
    //             this.setState(prevState => ({
    //                 ...prevState,
    //                 daftarKomponenPemeriksaan: prevState.daftarKomponenPemeriksaan.map(komponenState => ({
    //                     ...komponenState,
    //                     errorJumlahSampel: komponenState.id === komponen.id ? errorJumlahSampel : komponenState.errorJumlahSampel
    //                 }))
    //             }))
    //         } return null
    //     });
    //     return submitable;
    // }

    submitButton() {
        return (
            <div>
                <SirioButton purple
                             recommended={this.state.submitable}
                             disabled={!this.state.submitable}
                             classes="mx-1"
                             onClick={(event)  => this.handleSubmit(event, 2)}>
                    Simpan
                </SirioButton>
                <SirioButton purple
                             recommended={this.state.submitableDraft}
                             disabled={!this.state.submitableDraft}
                             classes="mx-1"
                             onClick={(event)  => this.handleSubmit(event, 1)}>
                    Draft
                </SirioButton>
                <SirioButton purple
                             classes="mx-1"
                             onClick={() => window.location.href = "/hasil-pemeriksaan"}>
                    Batal
                </SirioButton>
            </div>
        )
    }

    // Fungsi render SirioForm
    render() {
        return (
            <>
                {this.renderRedirect()}
                <SirioForm
                    title="Form Tambah Hasil Pemeriksaan"
                    betweenTitleSubtitle={this.getBetween()}
                    inputDefinition={this.outerInputDefinition()}
                    onSubmit={this.handleSubmit}
                    submitButton={this.submitButton()}
                />
            </>
        );
    }
}

export default withRouter(HasilPemeriksaanFormTambah);