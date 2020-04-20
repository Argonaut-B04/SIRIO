import React from 'react';
import SirioForm from '../SirioForm';
import SirioButton from '../../Button/SirioButton';
import HasilPemeriksaanService from '../../../Services/HasilPemeriksaanService';
import RisikoService from '../../../Services/RisikoService';
import { Redirect } from 'react-router-dom';
import { withRouter } from 'react-router-dom';
import RiskLevelService from "../../../Services/RiskLevelService";
import SirioField from "../SirioFormComponent/SirioField";
import SirioConfirmButton from "../../Button/ActionButton/SirioConfirmButton";
import TemuanRisikoService from "../../../Services/TemuanRisikoService";

class HasilPemeriksaanFormTambah extends React.Component {

    // Masukan user disimpan kedalam state sebelum dikirim ke backend
    constructor(props) {
        super(props);

        this.state = {
            daftarKomponenPemeriksaan: [],
            daftarRisikoKategori1: [],
            daftarRisikoKategori2: [],
            daftarHistoriTemuan: [],
            filterKategori: "",
            kategoriType: "",
            riskLevelOptionList: [],
            riskOptionList: [],
            submitableDraft: false,
            submitable: false,
            redirect: false,
        };

        this.renderRisikoKategori12 = this.renderRisikoKategori12.bind(this);
        this.renderRisikoKategori3 = this.renderRisikoKategori3.bind(this);
        this.renderRiskLevelOption = this.renderRiskLevelOption.bind(this);
        this.renderHistoriTemuan = this.renderHistoriTemuan.bind(this);
        this.handleChangeKomponen = this.handleChangeKomponen.bind(this);
        this.handleSelectChangeKomponen = this.handleSelectChangeKomponen.bind(this);
        this.handleSelectChange = this.handleSelectChange.bind(this);
        this.handleSelectChangeRisiko = this.handleSelectChangeRisiko.bind(this);
        this.handleMultiFieldChange = this.handleMultiFieldChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
        this.modifyFieldCount = this.modifyFieldCount.bind(this);
        this.setRedirect = this.setRedirect.bind(this);
        this.innerInputDefinition = this.innerInputDefinition.bind(this);
        this.validateKeteranganSampel = this.validateKeteranganSampel.bind(this);
        this.validateJumlahSampel = this.validateJumlahSampel.bind(this);
        this.validateRiskLevel = this.validateRiskLevel.bind(this);
        this.submitable = this.submitable.bind(this);
        this.submitableDraft = this.submitableDraft.bind(this);
    }

    componentDidMount() {
        this.renderRisikoKategori12();
        this.renderRisikoKategori3();
        this.renderRiskLevelOption();
        this.renderHistoriTemuan();
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

    async renderHistoriTemuan() {
        const response = await TemuanRisikoService.getHistoriTemuanRisikoKantorCabang(this.props.location.state.id);

        const daftarHistoriTemuan = response.data.result;

        this.setState({
            daftarHistoriTemuan: daftarHistoriTemuan
        })
    }

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
            .filter(risiko => risiko.kategori === 1)
            .map(risiko => {
                return (
                    {
                        label: risiko.nama,
                        value: risiko.id
                    }
                )
            });

        const risikoKategori2 = response.data.result
            .filter(risiko => risiko.kategori === 2)
            .map(risiko => {
                return (
                    {
                        label: risiko.nama,
                        value: risiko.id
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
                        risiko:risiko,
                        jumlahSampel:"",
                        keteranganSampel:"",
                        submitable:false,
                        submitableDraft:true,
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

    getSOPButton(indexKomponen) {
        return (
            <SirioButton
                purple
                hyperlinkLeft
                onClick={() => window.location.href = this.state.daftarKomponenPemeriksaan[indexKomponen].risiko.linkSop}
            >
                {this.state.daftarKomponenPemeriksaan[indexKomponen].risiko.namaSop}
            </SirioButton>
        )
    }

    getHistoriTemuanButton(idRisiko) {
        const histori = <p>
            {this.state.daftarHistoriTemuan
                .filter(temuan => temuan.idRisiko === idRisiko)
                .map((temuan, index) =>
                    <p className="text-center p-0 m-0">{index+1}. {temuan.keterangan} </p>
            )}
            </p>;

        return (
            <SirioConfirmButton
                purple recommended
                classes="m-1"
                modalTitle= "Riwayat Temuan Risiko"
                modalDesc={histori}
                customConfirmText=" "
                closeOnConfirm
                confirmDisable
            >
                Riwayat Temuan
            </SirioConfirmButton>
        )
    }

    innerInputDefinition(indexKomponen) {
        return (
            [
                {
                    label: "Komponen Risiko",
                    customInput: this.state.daftarKomponenPemeriksaan[indexKomponen].risiko.nama
                }, {
                    label: "SOP",
                    customInput: this.getSOPButton(indexKomponen)
                }, {
                    label: "Jumlah Sampel",
                    handleChange: (event) => this.handleChangeKomponen(event, indexKomponen),
                    type: "number",
                    name: "jumlahSampel",
                    min: 0,
                    value: this.state.daftarKomponenPemeriksaan[indexKomponen].jumlahSampel,
                    placeholder: "0",
                    validationFunction: (value) => this.validateJumlahSampel(value, indexKomponen)
                }, {
                    label: "Keterangan Sampel",
                    handleChange: (event) => this.handleChangeKomponen(event, indexKomponen),
                    type: "textarea",
                    name: "keteranganSampel",
                    value: this.state.daftarKomponenPemeriksaan[indexKomponen].keteranganSampel,
                    placeholder: "Keterangan sampel",
                    validationFunction: (value) => this.validateKeteranganSampel(value, indexKomponen)
                }, {
                    label: "Risk Level",
                    handleChange: (name, event) => this.handleSelectChangeKomponen(name, event, indexKomponen),
                    type: "select",
                    name: "idRiskLevel",
                    value: this.state.daftarKomponenPemeriksaan[indexKomponen].idRiskLevel,
                    optionList: this.state.riskLevelOptionList,
                    validationFunction: (value) => this.validateRiskLevel(value, indexKomponen)
                }, {
                    label: "Hasil Temuan",
                    multiple: true,
                    isMultipleObject: true,
                    handleChange: (event, index) => this.handleMultiFieldChange(event, index, indexKomponen, "keterangan"),
                    type: "textArea",
                    name: "daftarTemuanRisiko",
                    value: this.state.daftarKomponenPemeriksaan[indexKomponen].daftarTemuanRisiko.map(temuan => temuan.keterangan),
                    modifier: (name, newField, index) => this.modifyFieldCount(name, newField, index, indexKomponen, "keterangan"),
                }, {
                    label: "",
                    customInput: this.getHistoriTemuanButton(this.state.daftarKomponenPemeriksaan[indexKomponen].risiko.id)
                }, {
                    label: "Rekomendasi",
                    multiple: true,
                    isMultipleObject: true,
                    handleChange: (event, index) => this.handleMultiFieldChange(event, index, indexKomponen, "keterangan"),
                    type: "textArea",
                    name: "daftarRekomendasi",
                    value: this.state.daftarKomponenPemeriksaan[indexKomponen].daftarRekomendasi.map(rekomendasi => rekomendasi.keterangan),
                    modifier: (name, newField, index) => this.modifyFieldCount(name, newField, index, indexKomponen, "keterangan"),
                }
            ]
        )
    }

    outerInputDefinition() {
        if(this.state.daftarKomponenPemeriksaan.length > 0) {
            if (this.state.kategoriType === "1") {
                return (
                    this.state.daftarKomponenPemeriksaan
                        .reduce((accumulator, currentValue, currentIndex) => {
                            if(currentValue.risiko.grantParent === this.state.filterKategori) {
                                accumulator.push(
                                    {
                                        fullComponent:
                                            <SirioForm
                                                noHeader
                                                isInnerForm
                                                inputDefinition={this.innerInputDefinition(currentIndex)}
                                            />
                                    }
                                )
                            }
                            return accumulator
                        }, [])
                )
            } else if (this.state.kategoriType === "2") {
                return (
                    this.state.daftarKomponenPemeriksaan
                        .reduce((accumulator, currentValue, currentIndex) => {
                            if(currentValue.risiko.parent === this.state.filterKategori) {
                                accumulator.push(
                                    {
                                        fullComponent:
                                            <SirioForm
                                                noHeader
                                                isInnerForm
                                                inputDefinition={this.innerInputDefinition(currentIndex)}
                                            />
                                    }
                                )
                            }
                            return accumulator
                        }, [])
                )
            } else {
                return (
                    this.state.daftarKomponenPemeriksaan
                        .map((komponen, index) => {
                            return (
                                {
                                    fullComponent:
                                        <SirioForm
                                            noHeader
                                            isInnerForm
                                            inputDefinition={this.innerInputDefinition(index)}
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

    modifyFieldCount(name, newField, index, indexKomponen, objectKey) {
        const array = this.state.daftarKomponenPemeriksaan;
        if (newField.length > array[indexKomponen][name].length) {
            array[indexKomponen][name].push({
                [objectKey]: newField[newField.length - 1]
            })
        } else {
            array[indexKomponen][name].splice(index, 1)
        }

        this.setState({
            daftarKomponenPemeriksaan: array
        });
    }

    handleMultiFieldChange(event, index, indexKomponen, objectKey) {
        const targetName = event.target.name;
        const targetValue = event.target.value;
        const array = this.state.daftarKomponenPemeriksaan;
        array[indexKomponen][targetName][index][objectKey] = targetValue;

        this.setState({
            daftarKomponenPemeriksaan: array
        });
    }

    handleChangeKomponen(event, indexKomponen) {
        const { name, value } = event.target;
        const array = this.state.daftarKomponenPemeriksaan;
        array[indexKomponen][name] = value;

        this.setState({
            daftarKomponenPemeriksaan: array
        });
    }

    handleSelectChangeKomponen(name, event, indexKomponen) {
        const array = this.state.daftarKomponenPemeriksaan;
        array[indexKomponen][name] = event.value;

        this.setState({
            daftarKomponenPemeriksaan: array
        });
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

    submitable() {
        var submitable = true;
        this.state.daftarKomponenPemeriksaan.map(komponen => {
            submitable = submitable && komponen.submitable;
            return null
        });
        return submitable;
    }

    submitableDraft() {
        var submitableDraft = true;
        this.state.daftarKomponenPemeriksaan.map(komponen => {
            submitableDraft = submitableDraft && komponen.submitableDraft;
            return null
        });
        return submitableDraft;
    }

    validateKeteranganSampel(fokusKeteranganSampel, indexKomponen) {
        var submitable = true;
        var submitableDraft = true;
        var errorKeteranganSampel;
        const letter = /.*[a-zA-Z].*/;
        const array = this.state.daftarKomponenPemeriksaan;

        if (fokusKeteranganSampel === null || fokusKeteranganSampel === "") {
            submitable = false;
        } else if (!fokusKeteranganSampel.match(letter)) {
            submitable = false;
            submitableDraft = false;
            errorKeteranganSampel = "Ketarangan perlu mengandung huruf";
        }

        if (array[indexKomponen]["submitable"] !== submitable || array[indexKomponen]["submitableDraft"] !== submitableDraft) {
            array[indexKomponen]["submitable"] = submitable;
            array[indexKomponen]["submitableDraft"] = submitableDraft;
            this.setState({
                daftarKomponenPemeriksaan: array
            });
        }
        return errorKeteranganSampel;
    }

    validateJumlahSampel(fokusJumlahSampel, indexKomponen) {
        var submitable = true;
        var submitableDraft = true;
        var errorJumlahSampel;
        const number = /^[0-9]*$/;
        const array = this.state.daftarKomponenPemeriksaan;

        if (fokusJumlahSampel === null || fokusJumlahSampel === "") {
            submitable = false;
        } else if (!fokusJumlahSampel.match(number)) {
            submitable = false;
            submitableDraft = false;
            errorJumlahSampel = "Jumlah Sampel berupa angka";
        }

        if (array[indexKomponen]["submitable"] !== submitable || array[indexKomponen]["submitableDraft"] !== submitableDraft) {
            array[indexKomponen]["submitable"] = submitable;
            array[indexKomponen]["submitableDraft"] = submitableDraft;
            this.setState({
                daftarKomponenPemeriksaan: array
            });
        }
        return errorJumlahSampel;
    }

    validateRiskLevel(fokusRiskLevel, indexKomponen) {
        var submitable = true;
        var submitableDraft = true;
        var errorRiskLevel;
        const array = this.state.daftarKomponenPemeriksaan;

        if (fokusRiskLevel === null || fokusRiskLevel === "") {
            submitable = false;
        }

        if (array[indexKomponen]["submitable"] !== submitable || array[indexKomponen]["submitableDraft"] !== submitableDraft) {
            array[indexKomponen]["submitable"] = submitable;
            array[indexKomponen]["submitableDraft"] = submitableDraft;
            this.setState({
                daftarKomponenPemeriksaan: array
            });
        }
        return errorRiskLevel;
    }

    submitButton() {
        var tombolSimpan =
            <SirioButton
                purple
                disabled
                classes="mx-1"
            >
                Simpan
            </SirioButton>;
        if (this.submitable) {
            tombolSimpan =
                <SirioButton
                    purple
                    recommended
                    classes="mx-1"
                    onClick={(event)  => this.handleSubmit(event, 2)}>
                >
                    Simpan
                </SirioButton>
        }
        var tombolDraft =
            <SirioButton
                purple
                disabled
                classes="mx-1"
            >
                Draft
            </SirioButton>;
        if (this.submitableDraft) {
            tombolDraft =
                <SirioButton
                    purple
                    recommended
                    classes="mx-1"
                    onClick={(event)  => this.handleSubmit(event, 1)}>
                    >
                    Draft
                </SirioButton>
        }
        return (
            <div>
                {tombolSimpan}
                {tombolDraft}
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